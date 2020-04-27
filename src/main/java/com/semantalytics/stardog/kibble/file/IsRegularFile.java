
package com.semantalytics.stardog.kibble.file;

import com.complexible.stardog.plan.filter.ExpressionEvaluationException;
import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import org.openrdf.model.Value;
import java.nio.file.Files;

import java.io.IOException;
import java.nio.file.Paths;

import static com.complexible.common.rdf.model.Values.*;

public class IsRegularFile extends AbstractFunction implements UserDefinedFunction {

    IsRegularFile() {
        super(1, FileVocabulary.isRegularFile.stringValue());
    }

    private IsRegularFile(final IsRegularFile isRegularFile) {
        super(isRegularFile);
    }

    @Override
    protected Value internalEvaluate(final Value... values) throws ExpressionEvaluationException {

        final String file = assertStringLiteral(values[0]).stringValue();

        return literal(Files.isRegularFile(Paths.get(file)));
    }

    @Override
    public IsRegularFile copy() {
        return new IsRegularFile(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return FileVocabulary.isRegularFile.name();
    }
}
