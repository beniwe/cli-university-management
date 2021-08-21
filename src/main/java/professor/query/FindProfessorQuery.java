package professor.query;

import command.RecordToTableElement;
import org.example.models.tables.pojos.Professor;
import org.example.models.tables.records.ProfessorRecord;
import org.jooq.DSLContext;
import professor.storage.ProfessorRepository;
import query.Query;
import storage.PostgresConnectionFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.example.models.tables.Professor.PROFESSOR;

public class FindProfessorQuery implements Query<Optional<Professor>> {
    private ProfessorRepository repository;
    private Long professorId;

    public FindProfessorQuery(ProfessorRepository repository, Long professorId) {
        this.repository = repository;
        this.professorId = professorId;
    }

    @Override
    public Optional<Professor> execute() {
        return repository.findProfessorById(professorId);
    }

    public List<Professor> getProfessors() {
        DSLContext sql = PostgresConnectionFactory.build();

        List<Professor> result = new ArrayList<>();
        var records = sql.fetch(PROFESSOR);

        if (records.isEmpty()) {
            throw new IllegalStateException("(!) No Professors except you registered in the system");

        }

        for (ProfessorRecord currRecord : records) {
            if (!currRecord.getProfessorId().equals(professorId)) {
                result.add(RecordToTableElement.recordToProfessor(currRecord));
            }
        }

        return result;
    }

    public List<Professor> getNonAdminProfessors() {
        DSLContext sql = PostgresConnectionFactory.build();

        List<Professor> result = new ArrayList<>();
        var records = sql.fetch(PROFESSOR);

        if (records.isEmpty()) {
            throw new IllegalStateException("(!) No Professors except you registered in the system");

        }

        for (ProfessorRecord currRecord : records) {
            if (!currRecord.getProfessorId().equals(professorId) && !currRecord.getIsAdmin()) {
                result.add(RecordToTableElement.recordToProfessor(currRecord));
            }
        }

        if (result.isEmpty()) {
            throw new RuntimeException("(!) Every Professor in the System is already an admin");
        }

        return result;
    }
}
