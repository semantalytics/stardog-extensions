package com.semantalytics.stardog.kibble.util;

import com.semantalytics.stardog.kibble.AbstractStardogTest;
import org.junit.Test;
import org.openrdf.query.TupleQueryResult;

public class TestIndex  extends AbstractStardogTest {


    @Test
    public void testBindIndex() throws Exception {
    
     

            final String aQuery = "prefix util: + <" + UtilVocabulary.NAMESPACE + "> " +
                    "select ?result where { bind(util:index(?v) as ?result) values ?v {1 2 3 4 5} } order by ?v";

            final TupleQueryResult aResult = connection.select(aQuery).execute();

     

                //final String aValue = aResult.next().getValue("result").stringValue();
                while(aResult.hasNext()) {
                    System.out.println(aResult.next().getValue("result").stringValue());
                }
          
    }

}
