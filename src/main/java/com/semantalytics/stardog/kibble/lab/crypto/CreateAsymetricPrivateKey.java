package com.semantalytics.stardog.kibble.lab.crypto;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.string.StringFunction;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;
import org.apache.tika.parser.txt.CharsetDetector;
import org.openrdf.model.impl.NumericLiteral;

import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

import static com.stardog.stark.Values.literal;

public final class CreateAsymetricPrivateKey extends AbstractFunction implements StringFunction {

    protected CreateAsymetricPrivateKey() {
        super(2, "jfdkjfskfjsadjskaj");
    }

    private CreateAsymetricPrivateKey(final CreateAsymetricPrivateKey createAsymetricPrivateKey) {
        super(createAsymetricPrivateKey);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {

        if (assertStringLiteral(values[0])) {

            final String algorithm = ((Literal) values[0]).label();

            try {
                final KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(algorithm);
                if(assertNumericLiteral(values[1])) {
                    final int keysize = ((NumericLiteral)values[1]).intValue();
                    keyPairGenerator.initialize(keysize);
                    //blah generating pem headers/footers with strings is ugly
                    //bouncycastle instead?
                    return ValueOrError.General.of(literal(keyPairGenerator.generateKeyPair().getPrivate().getEncoded());
                } else {
                    return ValueOrError.Error;
                }
            } catch (NoSuchAlgorithmException e) {
                return ValueOrError.Error;
            }
            return ValueOrError.General.of(literal(detector.detect().getName()));
        } else {
            return ValueOrError.Error;
        }
    }

    @Override
    public CreateAsymetricPrivateKey copy() {
        return new CreateAsymetricPrivateKey(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return "kdjfdkfjds";
    }
}
