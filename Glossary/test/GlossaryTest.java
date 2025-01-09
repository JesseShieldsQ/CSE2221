import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.map.Map;
import components.map.Map1L;
import components.queue.Queue;
import components.queue.Queue1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

public class GlossaryTest {

    /*
     * tests for definitionToQueue
     */
    @Test
    public void testDefinitionToQueue() {
        String definition = "I, am definition";
        Queue<String> test = Glossary.definitionToQueue(definition);
        Queue<String> expected = new Queue1L<>();
        expected.enqueue("I,");
        expected.enqueue("am");
        expected.enqueue("definition");
        assertEquals(test, expected);

    }

    /*
     * tests for processInputFile
     */
    @Test
    public void testProcessInputFile() {
        SimpleReader index = new SimpleReader1L("data/index.txt");
        Map<String, Queue<String>> dictionary = new Map1L<>();
        Map<String, Queue<String>> expected = new Map1L<>();
        Queue<String> definition1 = new Queue1L<>();
        Queue<String> definition2 = new Queue1L<>();
        definition1.enqueue("the");
        definition1.enqueue("way,");
        definition1.enqueue("butt");
        definition2.enqueue("hi,");
        definition2.enqueue("hello");
        expected.add("butt", definition1);
        expected.add("hello", definition2);
        Glossary.processInputFile(dictionary, index);
        assertEquals(dictionary, expected);
        index.close();
    }
    /*
     * tests for outputIndexHeader
     */

    @Test
    public void testOutputIndexHeader() {
        SimpleWriter out = new SimpleWriter1L();
        Glossary.outputIndexHeader(out);
        out.close();
    }
}
