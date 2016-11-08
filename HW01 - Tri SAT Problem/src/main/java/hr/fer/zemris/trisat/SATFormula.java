package hr.fer.zemris.trisat;

public class SATFormula {
    private int numberOfVariables;
    private Clause[] clauses;

    public SATFormula(int numberOfVariables, Clause[] clauses) {
        this.numberOfVariables = numberOfVariables;
        this.clauses = clauses;
    }

    public int getNumberOfVariables() {
        return numberOfVariables;
    }

    public int getNumberOfClauses() {
        return clauses.length;
    }

    public Clause getClause(int index) {
        if (index < 0 || index > getNumberOfClauses()){
                throw new IndexOutOfBoundsException("There is no clause with the given index.");
        }

        return clauses[index];
    }

    public boolean isSatisfied(BitVector assignment) {
        for (Clause clause : clauses){
            if (!clause.isSatisfied(assignment)){
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (Clause clause : clauses){
            sb.append("(");
            sb.append(clause.toString());
            sb.append(") ");
        }

        return sb.toString();
    }
}
