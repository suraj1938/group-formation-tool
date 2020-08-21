package com.app.group15.Utility;

public class DatabaseQueriesUtility {

    // CourseInstructorMapperDao
    public static final String SELECT_ALL_COURSE_INSTRUCTOR_MAPPER = "SELECT * from table_course_instructor_mapper";
    public static final String GET_COURSE_INSTRUCTOR = "SELECT * FROM table_users tu\n" +
            "JOIN table_course_instructor_mapper tcm ON tu.id=tcm.instructor_id\n" +
            "WHERE tcm.course_id=?";
    public static final String DELETE_BY_COURSE_ID = "DELETE FROM table_course_instructor_mapper WHERE course_id=?";
    public static final String CHECK_COURSE_ID_EXISTS = "SELECT * FROM table_course_instructor_mapper WHERE course_id=?";
    public static final String ADD_INSTRUCTOR_FOR_COURSE_WITH_TA = "UPDATE table_course_instructor_mapper SET instructor_id=? WHERE course_id=?";
    public static final String ADD_TA_FOR_COURSE_WITH_INSTRUCTOR = "UPDATE table_course_instructor_mapper SET ta_id=? WHERE course_id=?";
    public static final String ADD_INSTRUCTOR_TO_COURSE = "INSERT INTO table_course_instructor_mapper(course_id,instructor_id) VALUES(?,?)";
    public static final String ADD_TA_TO_COURSE = "INSERT INTO table_course_instructor_mapper(course_id,ta_id) VAUES(?,?)";
    public static final String GET_COURSE_BY_TA = "select * from table_course_instructor_mapper tim\n" +
            "Join table_course tc on tc.id = tim.course_id \n" +
            "where tim.ta_id = ?";
    public static final String GET_COURSE_BY_INSTRUCTOR = "select * from table_course_instructor_mapper tim\n" +
            "Join table_course tc on tc.id = tim.course_id \n" +
            "where tim.instructor_id = ?";
    public static final String GET_COURSE_TA = "SELECT * FROM table_users tu\n" +
            "JOIN table_course_instructor_mapper tcm ON tu.id=tcm.ta_id\n" +
            "WHERE tcm.course_id=?";

    // CourseStudentMapperDao
    public static final String ADD_STUDENT_TO_COURSE = "INSERT INTO table_course_student_mapper(course_id,student_id) VALUES(?,?)";
    public static final String DELETE_BY_COURSE_ID_FROM_COURSE_STUDENT_MAPPER = "DELETE FROM table_course_student_mapper WHERE course_id=?";
    public static final String SELECT_ALL_FROM_COURSE_STUDENT_MAPPER = "SELECT * from table_course_student_mapper";
    public static final String GET_COURSE_ID_OF_STUDENT = "SELECT * from table_course_student_mapper WHERE student_id=?";

    // CourseDao
    public static final String GET_COURSE_FROM_ID = "SELECT * FROM table_course WHERE id=?";
    public static final String GET_ALL_COURSES = "SELECT * FROM table_course";
    public static final String SAVE_COURSE = "INSERT INTO table_course(name) VALUES(?)";
    public static final String UPDATE_COURSE = "UPDATE table_course SET name=? WHERE id=?";
    public static final String DELETE_COURSE = "DELETE FROM table_course WHERE id=?";

    // ForgetPasswordDao
    public static final String CHECK_IF_TOKEN_EXISTS = "SELECT id FROM table_forgot_password_tokens WHERE id=?";
    public static final String DELETE_TOKEN = "DELETE from table_forgot_password_tokens where id=?";
    public static final String INSERT_TOKEN = "INSERT INTO table_forgot_password_tokens(id,token,token_generation_date_time) "
            + "VALUES(?,?,?)";
    public static final String GET_USER_FROM_TOKEN = "SELECT * FROM table_forgot_password_tokens WHERE token like ?";
    public static final String UPDATE_PASSWORD = "UPDATE table_users SET password=? WHERE id=?";
    public static final String GET_USER_PASSWORD = "SELECT password from table_users  WHERE id=?";

    // UserDao
    public static final String GET_A_USER = "SELECT * FROM table_users WHERE id=?";
    public static final String GET_USER_BY_BANNER_ID = "SELECT * FROM table_users WHERE banner_id=?";
    public static final String GET_ALL_USERS = "SELECT * FROM table_users";
    public static final String SAVE_USER = "INSERT INTO table_users(first_name,last_name,email, banner_id,password) " + "VALUES(?,?,?,?,?)";
    public static final String UPDATE_USER = "UPDATE table_users SET first_name=?,last_name=?,email=? WHERE id=?";
    public static final String UPDATE_USER_ROLE = "UPDATE table_user_role_mapper SET role_id=? WHERE user_id=?";
    public static final String GET_PASSWORD_OF_USER = "SELECT password from table_users  WHERE id=?";
    public static final String GET_USER_BY_EMAIL = "SELECT * FROM table_users WHERE email like ?";

    //UserRoleDao
    public static final String GET_USER_ROLE = "SELECT * FROM table_user_roles WHERE id=?";
    public static final String GET_ROLE_ID = "SELECT * FROM table_user_roles WHERE role=?";
    public static final String GET_ROLES_BY_BANNER_ID = "SELECT role FROM table_users tu\n" +
            "JOIN table_user_role_mapper trm ON tu.id=trm.user_id\n" +
            "JOIN table_user_roles tr ON trm.role_id=tr.id\n" + "WHERE tu.banner_id=?";
    public static final String ADD_ROLE = "INSERT INTO table_user_role_mapper(user_id,role_id) VALUES(?,?)";
    public static final String UPDATE_ROLE = "UPDATE table_user_role_mapper SET role_id=? WHERE user_id=?";
    public static final String GET_ROLES = "SELECT * FROM table_user_roles";

}
