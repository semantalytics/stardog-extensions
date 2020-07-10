package com.semantalytics.stardog.kibble.crypto;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.google.common.collect.Range;
import com.google.common.io.BaseEncoding;
import com.idealista.fpe.FormatPreservingEncryption;
import com.idealista.fpe.builder.FormatPreservingEncryptionBuilder;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;

import java.util.Base64;

import static com.stardog.stark.Values.literal;

public final class FormatPreservingEncryptionEncryptFunction extends AbstractFunction implements UserDefinedFunction {

    private FormatPreservingEncryption formatPreservingEncryption;

    protected FormatPreservingEncryptionEncryptFunction() {
        super(3, CryptoVocabulary.formatPreservingEncrypt.toString());
    }

    private FormatPreservingEncryptionEncryptFunction(final FormatPreservingEncryptionEncryptFunction abbreviate) {
        super(abbreviate);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {

        if (!assertStringLiteral(values[0]) || !assertStringLiteral(values[1]) || !assertStringLiteral(values[2])) {
            return ValueOrError.Error;
        }

        final byte[] key = Base64.getDecoder().decode(((Literal)values[0]).label());
        final String plainText = ((Literal)values[1]).label();
        final byte[] tweak = ((Literal)values[2]).label().getBytes();

        if(formatPreservingEncryption == null) {
            formatPreservingEncryption = FormatPreservingEncryptionBuilder
                    .ff1Implementation()
                    .withDefaultDomain()
                    .withDefaultPseudoRandomFunction(key)
                    .withDefaultLengthRange()
                    .build();
        }
        return ValueOrError.General.of(literal(formatPreservingEncryption.encrypt(plainText, tweak)));
    }

    @Override
    public FormatPreservingEncryptionEncryptFunction copy() {
        return new FormatPreservingEncryptionEncryptFunction(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return CryptoVocabulary.formatPreservingEncrypt.toString();
    }
}
