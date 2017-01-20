package utils;

import utils.Container;

import java.util.List;

/**
 * Created by admin on 20.01.2017.
 */
public class StudentRepository implements Container{
    private String studentId[];

    public StudentRepository(String[] studentId) {
        this.studentId = studentId;
    }

    public String[] getStudentId() {
        return studentId;
    }

    public void setStudentId(String[] studentId) {
        this.studentId = studentId;
    }

    @Override
    public Iterator getIterator() {
        return new StudentIterator();
    }

    public class StudentIterator implements Iterator {

        int index;

        @Override
        public boolean hasNext() {

            if(index < studentId.length){
                return true;
            }
            return false;
        }

        @Override
        public Object next() {

            if(this.hasNext()){
                return studentId[index++];
            }
            return null;
        }
    }
}

