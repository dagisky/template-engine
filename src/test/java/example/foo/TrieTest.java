package example.foo;

import com.aa.ticketing.Trie;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class TrieTest {

    @Test
    public void shouldAddParmMap(){
        Trie trie = new Trie();

        trie.addParam("user.address.address1", "10808 Stone Canyon Rd");
        trie.addParam("user.address.address2", "1234");
        trie.addParam("user.address.zip", "72323");
        trie.addParam("user.fname", "FooMan");
        trie.addParam("user.lname", "Lolo");

        Assertions.assertEquals(
                trie.getParams("user.address").getOrDefault("address1", null),
                "10808 Stone Canyon Rd");
        Assertions.assertEquals(
                trie.getParams("user.address").getOrDefault("address2", null),
                "1234");
        Assertions.assertEquals(
                trie.getParams("user").getOrDefault("fname", null),
                "FooMan");
        Assertions.assertEquals(
                trie.getParams("user").getOrDefault("lname", null),
                "Lolo");
    }
}
