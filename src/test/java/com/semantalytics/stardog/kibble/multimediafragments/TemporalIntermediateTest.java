package com.semantalytics.stardog.kibble.multimediafragments;

import com.github.tkurz.media.fragments.base.MediaFragmentURI;
import com.github.tkurz.media.fragments.exceptions.MediaFragmentURISyntaxException;
import com.github.tkurz.media.fragments.temporal.NPTFragment;
import com.google.common.collect.ImmutableList;
import com.google.common.io.Resources;
import com.semantalytics.stardog.kibble.AbstractStardogTest;
import com.semantalytics.stardog.kibble.multimediafragments.Constants;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;
import com.stardog.stark.io.RDFFormats;
import com.stardog.stark.query.BindingSet;
import com.stardog.stark.query.SelectQueryResult;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

import static com.complexible.stardog.plan.filter.functions.AbstractFunction.assertStringLiteral;
import static org.assertj.core.api.Assertions.assertThat;

public class TemporalIntermediateTest extends AbstractStardogTest {

    @Test
    public void testIntermediate() throws IOException, MediaFragmentURISyntaxException {
        final InputStream in = Resources.getResource("test1.ttl").openStream();
        connection.begin();
        connection.add().io().format(RDFFormats.TURTLE).stream(in);
        connection.commit();

        String query =
                "PREFIX ma: <http://www.w3.org/ns/ma-ont#>" +
                        "PREFIX mm: <" + Constants.NAMESPACE + ">" +
                        "SELECT ?f1 ?f2 (mm:temporalIntermediate(?f1,?f2) AS ?box) WHERE {" +
                        "   ?f1 rdfs:label \"1_1\"." +
                        "   ?f2 rdfs:label \"1_3\"." +
                        "}";
        List<BindingSet> list;
        try (final SelectQueryResult aResult = connection.select(query).execute()) {

            list = ImmutableList.copyOf(aResult);

        Assert.assertTrue(list.size() == 1);

        for(BindingSet set : list) {
            String box = set.literal("box").get().label();

            NPTFragment fragment = (NPTFragment)(new MediaFragmentURI(box)).getMediaFragment().getTemporalFragment();

            Assert.assertEquals(5.0,fragment.getStart().getValue(),0);
            Assert.assertEquals(15.0,fragment.getEnd().getValue(),0);
        }
        }
    }

}