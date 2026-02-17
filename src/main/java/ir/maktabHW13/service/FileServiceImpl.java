package ir.maktabHW13.service;

import ir.maktabHW13.model.Exam;
import ir.maktabHW13.model.MultiOption;
import ir.maktabHW13.model.MultipleChoiceQuestion;
import ir.maktabHW13.model.Questions;
import ir.maktabHW13.repository.ExamRepository;

import java.io.*;
import java.util.List;

public class FileServiceImpl implements FilesService {

    private ExamRepository examRepository;

    public FileServiceImpl(ExamRepository examRepository) {
        this.examRepository = examRepository;
    }

    @Override
    public File exportFile(Long examId) throws IOException {
        Exam exam = examRepository.findById(Exam.class, examId);
        if (exam == null) {
            throw new IllegalArgumentException(" exam is null");

        }
        String fileName = exam.getTitle_Exam() + ".txt";
        File file = new File(fileName);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(" Id is : " + exam.getId().toString());
            writer.newLine();


            writer.write(" title is : " + exam.getTitle_Exam());
            writer.newLine();


            writer.write(" questions is : ");
            writer.newLine();

            List<Questions> questions = exam.getQuestions();
            if (questions.isEmpty()) {
                writer.write(" no questions found");
                writer.newLine();

            }
            for (Questions question : questions) {
                writer.write(question.getTitle());

                writer.newLine();

                writer.write(question.getQuestionText());
                if (questions instanceof MultipleChoiceQuestion) {
                    MultipleChoiceQuestion multipleChoiceQuestion = (MultipleChoiceQuestion) question;
                    writer.write(" options :");
                    for (MultiOption option : multipleChoiceQuestion.getOptions()) {
                        option.setOptionNumber(option.getOptionNumber() + 1);
                    }
                    writer.newLine();
                    writer.write("correct options :" + multipleChoiceQuestion.getCorrectOptionIndex());

                }
            }
            System.out.println("Questions for Exam ID " + examId + " exported to " + fileName + " successfully.");

            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
        return file;
    }
}
