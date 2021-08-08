package student.query;

import static org.example.models.Tables.*;

import command.RecordToTableElement;
import org.example.models.tables.pojos.Course;
import org.example.models.tables.pojos.DegreeProgram;
import org.jooq.DSLContext;
import query.Query;

import java.util.Optional;

public class FindDegreeProgramQuery implements Query<Optional<DegreeProgram>> {
    private DSLContext sql;
    private int id;

    public FindDegreeProgramQuery(DSLContext sql, Integer id) {
        this.sql = sql;
        this.id = id;
    }

    @Override
    public Optional<DegreeProgram> execute() {
        var programRecord = sql.fetchOne(DEGREE_PROGRAM, DEGREE_PROGRAM.PROGRAM_ID.eq(id));

        if (programRecord == null) {
            return Optional.empty();
        }

        DegreeProgram program = RecordToTableElement.recordToProgram(programRecord);

        return Optional.of(program);
    }
}
