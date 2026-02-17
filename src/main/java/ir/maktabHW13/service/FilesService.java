package ir.maktabHW13.service;

import java.io.File;
import java.io.IOException;

public interface FilesService {


    File exportFile(Long examId) throws IOException;

}
