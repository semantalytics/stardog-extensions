package com.semantalytics.stardog.kibble.lab.crypto;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.stardog.stark.Value;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import static com.stardog.stark.Values.literal;
import static sun.security.x509.CertificateAlgorithmId.ALGORITHM;

public class AesEncrypt extends AbstractFunction implements UserDefinedFunction {

    public AesEncrypt() {
        super(2, "http://semantalytics.com/2016/03/ns/stardog/udf/util/AesEncrypt");
    }

    public AesEncrypt(final AesEncrypt aesEncrypt) {
        super(aesEncrypt);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {

        String encryptedVal;
        try {
        final String secKey = assertStringLiteral(values[0]).stringValue();
        final String valueEnc = assertStringLiteral(values[1]).stringValue();

        KeyGenerator keyGenerator = null;
            keyGenerator = KeyGenerator.getInstance("AES/CTR/PKCS5PADDING");
        keyGenerator.init(128);
        SecretKey secretKey = keyGenerator.generateKey();
        Cipher cipher = null;
            cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);

            final Key key = generateKeyFromString(secKey);
            final Cipher c = Cipher.getInstance(ALGORITHM);
            c.init(Cipher.ENCRYPT_MODE, key);
            final byte[] encValue = c.doFinal(valueEnc.getBytes());
            encryptedVal = new BASE64Encoder().encode(encValue);
        } catch (IOException | InvalidKeyException | BadPaddingException | IllegalBlockSizeException | NoSuchAlgorithmException | NoSuchPaddingException e) {
            return ValueOrError.Error;
        }

        return ValueOrError.General.of(literal(encryptedVal));
    }

    @Override
    public AesEncrypt copy() {
        return new AesEncrypt(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return "aesEnrypt";
    }

    private Key generateKeyFromString(final String secKey) throws IOException {
        final byte[] keyVal = new BASE64Decoder().decodeBuffer(secKey);
        return new SecretKeySpec(keyVal, ALGORITHM);
    }

}
