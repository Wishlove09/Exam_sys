package edu.xmut.examsys.bean;

import java.io.Serializable;

import lombok.Data;

/**
 * @TableName exam_participants
 */
@Data
public class ExamParticipants implements Serializable {
    /**
     *
     */
    private Integer id;

    /**
     * 考试id
     */
    private Long examId;

    /**
     * 班级id
     */
    private Long classId;

    private static final long serialVersionUID = 1L;
}