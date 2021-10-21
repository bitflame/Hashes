import spock.lang.Specification

class GfGSpecification extends Specification {
    def "ShouldCalculateRunLength"() {
        GfG runLengthInstance = new GfG();
        expect:
        runLengthInstance.encode("a") == "a1";
        runLengthInstance.encode("aaaabbbccc") == "a4b3c3";
        runLengthInstance.encode("abbbcdddd") == "a1b3c1d4";
    }

    def "GivenWhenThenShouldWork"() {
        given:
        def GfG runLengthInstance = new GfG();

        when:
        String s = "aaaabbbccc";

        then:
        runLengthInstance.encode(s) == "a4b3c3";
    }

    def "Encode() Should expect valid String"() {
        given:
        def GfG runLengthInstance = new GfG();

        when:
        runLengthInstance.encode("");

        then:
        thrown(IllegalArgumentException);
    }

    def "GfG decoder should work"() {
        given:
        def GfG runLengthInstance = new GfG();
        result == runLengthInstance.decode_v2(str)

        where:
        str        | result
        A10        | AAAAAAAAAA
        A3B1C2D1E1 | AAABCCDE



    }
}
