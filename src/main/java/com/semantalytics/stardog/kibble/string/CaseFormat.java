package com.semantalytics.stardog.kibble.string;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.string.StringFunction;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;

import java.util.Optional;

import static com.google.common.base.CaseFormat.*;
import static com.stardog.stark.Values.literal;

public final class CaseFormat extends AbstractFunction implements StringFunction {

    protected CaseFormat() {
        super(3, StringVocabulary.caseFormat.toString());
    }

    private CaseFormat(final CaseFormat caseFormat) {
        super(caseFormat);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {

        final ValueOrError result;

        if (assertStringLiteral(values[0]) && assertStringLiteral(values[1]) && assertStringLiteral(values[2])) {

            final String caseFormatString = ((Literal) values[0]).label();

            final Optional<com.google.common.base.CaseFormat> caseFormatFrom = getFromFormatType(((Literal) values[1]).label());
            final Optional<com.google.common.base.CaseFormat> caseFormatTo = getToFormatType(((Literal) values[2]).label());

            if (caseFormatFrom.isPresent() && caseFormatTo.isPresent()) {

                switch (caseFormatFrom.get()) {
                    case LOWER_CAMEL:
                        return ValueOrError.General.of(literal(caseFormatFrom.get().LOWER_CAMEL.to(caseFormatTo.get(), caseFormatString)));
                    case UPPER_CAMEL:
                        return ValueOrError.General.of(literal(caseFormatFrom.get().UPPER_CAMEL.to(caseFormatTo.get(), caseFormatString)));
                    case LOWER_HYPHEN:
                        return ValueOrError.General.of(literal(caseFormatFrom.get().LOWER_HYPHEN.to(caseFormatTo.get(), caseFormatString)));
                    case LOWER_UNDERSCORE:
                        return ValueOrError.General.of(literal(caseFormatFrom.get().LOWER_UNDERSCORE.to(caseFormatTo.get(), caseFormatString)));
                    case UPPER_UNDERSCORE:
                        return ValueOrError.General.of(literal(caseFormatFrom.get().UPPER_UNDERSCORE.to(caseFormatTo.get(), caseFormatString)));
                    default:
                        result = ValueOrError.Error;
                }
            } else {
                result = ValueOrError.Error;
            }
        } else {
            result = ValueOrError.Error;
        }
        return result;
    }

    @Override
    public CaseFormat copy() {
        return new CaseFormat(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return StringVocabulary.caseFormat.toString();
    }

    public Optional<com.google.common.base.CaseFormat> getFromFormatType(final String formatString) {

        final com.google.common.base.CaseFormat caseFormat;

        switch(formatString) {
            case "fromFormat":
                caseFormat =  LOWER_CAMEL;
                break;
            case "FromFormat":
                caseFormat = UPPER_CAMEL;
                break;
            case "from-format":
                caseFormat = LOWER_HYPHEN;
                break;
            case "from_format":
                caseFormat = LOWER_UNDERSCORE;
                break;
            case "FROM_FORMAT":
                caseFormat = UPPER_UNDERSCORE;
                break;
            default:
                caseFormat = null;
        }
        return Optional.ofNullable(caseFormat);
    }

    public Optional<com.google.common.base.CaseFormat> getToFormatType(final String formatString) {

        final com.google.common.base.CaseFormat caseFormat;

        switch(formatString) {
            case "toFormat":
                caseFormat = LOWER_CAMEL;
                break;
            case "ToFormat":
                caseFormat = UPPER_CAMEL;
                break;
            case "to-format":
                caseFormat = LOWER_HYPHEN;
                break;
            case "to_format":
                caseFormat = LOWER_UNDERSCORE;
                break;
            case "TO_FORMAT":
                caseFormat = UPPER_UNDERSCORE;
                break;
            default:
                caseFormat = null;
        }
        return Optional.ofNullable(caseFormat);
    }
}
