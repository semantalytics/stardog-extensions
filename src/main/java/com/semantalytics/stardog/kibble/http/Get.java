package com.semantalytics.stardog.kibble.http;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.stardog.stark.Datatype;
import com.stardog.stark.Value;
import com.stardog.stark.Values;
import org.apache.commons.io.IOUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Base64;

public class Get extends AbstractFunction implements UserDefinedFunction {

    public Get() {
        super(1, HttpVocabulary.get.toString());
    }

    public Get(final Get get) {
        super(get);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {

        final ByteArrayOutputStream baos;
        if (assertIRI(values[0])) {
            try {
                final URLConnection conn;
                conn = new URL(values[0].toString()).openConnection();
                conn.setConnectTimeout(5000);
                conn.setReadTimeout(5000);
                conn.connect();
                baos = new ByteArrayOutputStream();
                IOUtils.copy(conn.getInputStream(), baos);
                return ValueOrError.General.of(Values.literal(Base64.getEncoder().encodeToString(baos.toByteArray()), Datatype.BASE64BINARY));
            } catch (IOException e) {
                return ValueOrError.Error;
            }
        } else {
            return ValueOrError.Error;
        }
    }

    @Override
    public Get copy() {
        return new Get(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }
}
