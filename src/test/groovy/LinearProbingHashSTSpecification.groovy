import spock.lang.Specification

class LinearProbingHashSTSpecification extends Specification {
    def "should be a simple assertion"() {
        given:
        Exercise20.LinearProbingHashST l = new Exercise20.LinearProbingHashST();
        when:
        l.put('S', 0)
        then:
        l.contains('S').equals(true);
    }


}
