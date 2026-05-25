package hsf301.fe.services;

import hsf301.fe.pojos.Student;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl implements StudentService {

    @Override
    public void save(Student student) {
        System.out.println("Save Student...");
    }
}
