package example.foo;

import com.aa.ticketing.JsonTemplateRenderer;
import com.aa.ticketing.Trie;
import org.junit.jupiter.api.Test;

public class JsonTemplateRendererTest {

    @Test
    public void shouldBuildTemplateGraph(){
        JsonTemplateRenderer renderer = new JsonTemplateRenderer("basetemplate");
        Trie dataTable = new Trie();
        dataTable.addParam("fname", "FooMan");
        dataTable.addParam("address.address1", "10234 Stone Canyon Rd");
        dataTable.addParam("address.address2", "1234");
        dataTable.addParam("address.zip", "3455");
        dataTable.addParam("address.context.country", "XYZ");
        dataTable.addParam("context.retrieveReservationResponse.airReservationLastFetch.recordLocator", "XYZ");
        renderer.render(dataTable);
    }
}
