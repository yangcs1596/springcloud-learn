package com.safedog.cloudnet.model;

import java.util.function.Consumer;

/**
 * @author ycs
 * @description
 * @date 2022/2/18 15:16
 */
public final class FastAutoGeneratorStudent {
    private final SchoolModel.SchoolModelBuilder schoolModelBuilder;
    private final ClassModel.ClassModelBuilder classModelBuilder;
    private final TeacherModel.TeacherModelBuilder teacherModelBuilder;

    public FastAutoGeneratorStudent(SchoolModel.SchoolModelBuilder schoolModelBuilder,
                                    ClassModel.ClassModelBuilder classModelBuilder,
                                    TeacherModel.TeacherModelBuilder teacherModelBuilder) {
        this.schoolModelBuilder = schoolModelBuilder;
        this.classModelBuilder = classModelBuilder;
        this.teacherModelBuilder = teacherModelBuilder;
    }

    public static FastAutoGeneratorStudent create(String name){
        return new FastAutoGeneratorStudent(SchoolModel.builder(), ClassModel.builder(), TeacherModel.builder());
    }

    public FastAutoGeneratorStudent schoolModel(Consumer<SchoolModel.SchoolModelBuilder> consumer){
        consumer.accept(this.schoolModelBuilder);
        return this;
    }
    public FastAutoGeneratorStudent classModel(Consumer<ClassModel.ClassModelBuilder> consumer){
        consumer.accept(this.classModelBuilder);
        return this;
    }
    public FastAutoGeneratorStudent teacherModel(Consumer<TeacherModel.TeacherModelBuilder> consumer){
        consumer.accept(this.teacherModelBuilder);
        return this;
    }

    public void excute(){
        //这里可以封装另一个类， 组装该类build需要的参数实体， 然后写个excute执行对应的业务方法即可。
    }

}
