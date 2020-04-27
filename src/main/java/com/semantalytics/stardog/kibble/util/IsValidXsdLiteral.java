package com.semantalytics.stardog.kibble.util;

import com.complexible.stardog.plan.filter.ExpressionEvaluationException;
import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import org.openrdf.model.Literal;
import org.openrdf.model.Value;
import org.openrdf.model.datatypes.XMLDatatypeUtil;

import static com.complexible.common.rdf.model.Values.*;

public class IsValidXsdLiteral extends AbstractFunction implements UserDefinedFunction {

    public IsValidXsdLiteral() {
        super(1, UtilVocabulary.isValidXsdLiteral.stringValue());
    }

    private IsValidXsdLiteral(final IsValidXsdLiteral isValidXsdLiteral) {
        super(isValidXsdLiteral);
    }

    @Override
    protected Value internalEvaluate(final Value... values) throws ExpressionEvaluationException {
        final Literal literal = assertLiteral(values[0]);

        return literal(XMLDatatypeUtil.isValidValue(literal.stringValue(), literal.getDatatype()));
    }

    @Override
    public IsValidXsdLiteral copy() {
        return new IsValidXsdLiteral(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return UtilVocabulary.isValidXsdLiteral.name();
    }
}
