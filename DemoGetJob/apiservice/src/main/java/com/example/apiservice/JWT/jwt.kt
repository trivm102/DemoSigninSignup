import android.util.Base64
import android.util.Log
import org.json.JSONObject
import java.nio.charset.Charset
import java.security.KeyFactory
import java.security.Signature
import java.security.spec.PKCS8EncodedKeySpec
import kotlin.experimental.and

class JWT (claim: HashMap<String, Any?>) {
    var claim: HashMap<String, Any?>
    var header = "{\"alg\":\"ES256\",\"typ\":\"JWT\"}"
    val charset = Charset.forName("UTF-8")

    init{
        this.claim = claim
    }


    fun sign(key: String): String {
        var base64Flags = Base64.URL_SAFE or Base64.NO_PADDING or Base64.NO_WRAP

        var cleanedKey = Base64.decode((key), base64Flags).toString(charset)
        cleanedKey = cleanedKey.replace("-----BEGIN PRIVATE KEY-----\n", "")
        cleanedKey = cleanedKey.replace("-----END PRIVATE KEY-----\n", "")
        val factory = KeyFactory.getInstance("EC")
        val keySpec = PKCS8EncodedKeySpec(Base64.decode(cleanedKey, Base64.DEFAULT))
        val key = factory.generatePrivate(keySpec)

        val claimString = JSONObject(claim).toString()
        val base64Header = Base64.encode(header.toByteArray(charset), base64Flags).toString(charset)
        val base64Payload = Base64.encode(claimString.toByteArray(charset), base64Flags).toString(charset)
        val message = "$base64Header.$base64Payload"
        Log.d("JWT", key.toString())
        val s = Signature.getInstance("SHA256withECDSA")
            .apply {
                initSign(key)
                update(message.toByteArray(charset))
            }
        val sigDer: ByteArray = s.sign()
        val sig = transcodeSignatureToConcat(sigDer, 64)
        val jwtSig = Base64.encode(sig, base64Flags).toString(charset)
        return "$message.$jwtSig"
    }


    @Throws(Exception::class)
    private fun transcodeSignatureToConcat(derSignature: ByteArray, outputLength: Int): ByteArray? {
        if (derSignature.size < 8 || derSignature[0].toInt() != 48) {
            throw Exception("Invalid ECDSA signature format")
        }
        val offset: Int
        offset = if (derSignature[1] > 0) {
            2
        } else if (derSignature[1] == 0x81.toByte()) {
            3
        } else {
            throw Exception("Invalid ECDSA signature format")
        }

        val rLength = derSignature[offset + 1]
        var i = rLength.toInt()

        while (i > 0 && derSignature[offset + 2 + rLength - i].toInt() == 0) {
            i--
        }

        val sLength = derSignature[offset + 2 + rLength + 1]
        var j = sLength.toInt()
        while (j > 0 && derSignature[offset + 2 + rLength + 2 + sLength - j].toInt() == 0) {
            j--
        }
        var rawLen = Math.max(i, j)
        rawLen = Math.max(rawLen, outputLength / 2)

        if ((derSignature[offset - 1] and 0xff.toByte()).toInt() != derSignature.size - offset
            || (derSignature[offset - 1] and 0xff.toByte()).toInt() != 2 + rLength + 2 + sLength
            || derSignature[offset].toInt() != 2
            || derSignature[offset + 2 + rLength].toInt() != 2
        ) {
            throw Exception("Invalid ECDSA signature format")
        }

        val concatSignature = ByteArray(2 * rawLen)
        System.arraycopy(
            derSignature,
            offset + 2 + rLength - i,
            concatSignature,
            rawLen - i,
            i
        )
        System.arraycopy(
            derSignature,
            offset + 2 + rLength + 2 + sLength - j,
            concatSignature,
            2 * rawLen - j,
            j
        )
        return concatSignature
    }
}