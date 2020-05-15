package com.study.evaluation.dao;

import com.study.evaluation.bean.EvaluationBean;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * description
 *
 * @author Hu.Wang 2020/04/27 15:34
 */
@Mapper
public interface EvaluationDao {

    /**
     * 插入一条评价信息
     */
    @Insert("insert into evaluation"
                    + "(evaluation_people_no,course_id,evaluation_time,role_id,evaluation_score,evaluation_advice) "
                    + "values(#{personNo},#{courseId},NOW(),#{type},#{score},#{advice}); ")
    boolean submitEvaluation(@Param("personNo") int personNo, @Param("courseId") int courseId, @Param("type") int type,
                    @Param("score") double score, @Param("advice") String advice);

    /**
     * 插入各项指标得分
     */
    @Insert("insert into evaluation_index values(#{evaluationId},#{indexId},#{indexScore});")
    boolean insertIndexScore(@Param("evaluationId") int evaluationId, @Param("indexId") int indexId,
                    @Param("indexScore") double indexScore);

    /**
     * 查询最新插入的自增id
     */
    @Select("SELECT LAST_INSERT_ID( );")
    int getInsertId();

    /**
     * 根据评价id更新评价总分
     */
    @Update("update evaluation set evaluation_score = #{score} where evaluation_id=#{id}")
    boolean updateEvaluation(@Param("score") double score, @Param("id") int id);

    /**
     * 更新学生课程的评价状态
     */
    @Update("update student_course set status=#{status} where mid_id = #{midId} and student_id in " +
            "(select student_id from student where student_no = #{studentNo})")
    boolean updateStuCourse(@Param("midId") int midId, @Param("studentNo") int studentNo, @Param("status") int status);

    /**
     * 更新教师课程的评价状态
     */
    @Update("update teacher_course set status=#{status} where mid_id = #{midId}")
    boolean updateTeaCourse(@Param("midId") int midId, @Param("status") int status);

    /**
     * 根据指标等级查询所有指标的个数
     */
    @Select("select COUNT(*) from `index` where index_grade = #{grade}")
    int getIndexSize(@Param("grade") int grade);

    /**
     * 查询所有的教师评价和同行评价
     */
    @Select("SELECT evaluation_id,evaluation_people_no,"
                    + "( SELECT teacher_name FROM teacher WHERE teacher_no = evaluation_people_no ) AS evaluation_people_name,"
                    + "course_id,( SELECT course_name FROM course c WHERE c.course_id = e.course_id ) AS course_name,"
                    + "evaluation_time,role_id,(select role_name from `role` where role_id=e.role_id)as role_name,"
                    + "evaluation_score,evaluation_advice FROM evaluation e where role_id=0 " + "or role_id=1; ")
    List<EvaluationBean> selectTeaEvaluation();

    /**
     * 查询所有的学生评价
     */
    @Select("SELECT evaluation_id,evaluation_people_no,"
                    + "( SELECT student_name FROM student WHERE student_no = evaluation_people_no ) AS evaluation_people_name,"
                    + "course_id,( SELECT course_name FROM course c WHERE c.course_id = e.course_id ) AS course_name,"
                    + "evaluation_time,role_id,(select role_name from `role` where role_id=e.role_id)as role_name,"
                    + "evaluation_score,evaluation_advice FROM evaluation e where role_id=2; ")
    List<EvaluationBean> selectStuEvaluation();


    /**
     * 删除评价
     */
    @Delete("delete from evaluation where evaluation_id=#{id};")
    int deleteById(@Param("id") int id);


    @Delete("delete from evaluation_index where evaluation_id=#{id};")
    int deleteIndexById(@Param("id") int id);

    /**
     * 导入评价
     */
    @Insert("insert into evaluation(evaluation_people_no,course_id,evaluation_time,evaluation_score,evaluation_advice,role_id) "
                    + "values(#{bean.evaluationPeopleNo},#{bean.courseId},#{bean.evaluationTime},#{bean.evaluationScore},#{bean.evaluationAdvice},#{bean.roleId});")
    int insert(@Param("bean") EvaluationBean bean);

    /**
     * 查询一级指标的平均得分
     */
    @Select("select avg(index_score) from evaluation_index where index_id=#{indexId} and evaluation_id in (select evaluation_id from evaluation where course_id = #{courseId})")
    Double selectOneIndexScore(@Param("courseId") int courseId, @Param("indexId") int indexId);

}
