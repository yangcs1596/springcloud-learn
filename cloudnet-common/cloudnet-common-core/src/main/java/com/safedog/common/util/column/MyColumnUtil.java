package com.safedog.common.util.column;

import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.core.toolkit.support.SerializedLambda;

import java.io.*;
import java.util.function.Function;

/**
 * @author ycs
 * @description
 * @date 2022/2/10 10:01
 */
public class MyColumnUtil {
    @FunctionalInterface
    public interface IGetSegment<T, R> extends Function<T, R>, Serializable{
    }

    public static <T> String getFieldName(SFunction<T, ?> lambda){
        return getSerializedLambda(lambda).getImplMethodName();
    }

    /**
     * mybatis-plus获取属性值的源码
     * @param lambda
     * @return
     */
    public static SerializedLambda getSerializedLambda(SFunction lambda) {
        SerializedLambda serializedLambda = null;
        try {
            ObjectInputStream objIn = new ObjectInputStream(new ByteArrayInputStream(serialize(lambda))) {
                @Override
                protected Class<?> resolveClass(ObjectStreamClass objectStreamClass) throws IOException, ClassNotFoundException {
                    Class<?> clazz = super.resolveClass(objectStreamClass);
                    return clazz == java.lang.invoke.SerializedLambda.class ? SerializedLambda.class : clazz;
                }
            };
            serializedLambda  = (SerializedLambda) objIn.readObject();
        } catch (Exception x) {
        }
        return serializedLambda;
    }

    private static byte[] serialize(Object object) {
        if (object == null) {
            return null;
        } else {
            ByteArrayOutputStream baos = new ByteArrayOutputStream(1024);
            try {
                ObjectOutputStream oos = new ObjectOutputStream(baos);
                oos.writeObject(object);
                oos.flush();
            } catch (IOException var3) {
                throw new IllegalArgumentException("Failed to serialize object of type: " + object.getClass(), var3);
            }

            return baos.toByteArray();
        }
    }

//    public class ConditionApp {
//        @FunctionalInterface
//        public interface CustomerFunction<S, T> extends Serializable {
//            T convert(S source);
//        }
//
//        @Data
//        public static class User {
//            private String name;
//            private String site;
//        }
//
//        public static void main(String[] args) throws Exception {
//            Condition c1 = addCondition(User::getName, "=", "throwable");
//            System.out.println("c1 = " + c1);
//            Condition c2 = addCondition(User::getSite, "IN", "('throwx.cn','vlts.cn')");
//            System.out.println("c1 = " + c2);
//        }
//
//        private static <S> Condition addCondition(CustomerFunction<S, String> function, String operation, Object value) throws Exception {
//            Condition condition = new Condition();
//            Method method = function.getClass().getDeclaredMethod("writeReplace");
//            method.setAccessible(true);
//            SerializedLambda serializedLambda = (SerializedLambda) method.invoke(function);
//            String implMethodName = serializedLambda.getImplMethodName();
//            int idx;
//            if ((idx = implMethodName.lastIndexOf("get")) >= 0) {
//                condition.setField(Character.toLowerCase(implMethodName.charAt(idx + 3)) + implMethodName.substring(idx + 4));
//            }
//            condition.setEntityKlass(Class.forName(serializedLambda.getImplClass().replace("/", ".")));
//            condition.setOperation(operation);
//            condition.setValue(value);
//            return condition;
//        }
//
//        @Data
//        private static class Condition {
//            private Class<?> entityKlass;
//            private String field;
//            private String operation;
//            private Object value;
//        }
//    }// 执行结果c1 = ConditionApp.Condition(entityKlass=class club.throwable.lambda.ConditionApp$User, field=name, operation==, value=throwable)c1 = ConditionApp.Condition(entityKlass=class club.throwable.lambda.ConditionApp$User, field=site, operation=IN, value=('throwx.cn','vlts.cn'))
// 反射方式
//public class ReflectionSolution {
//    @FunctionalInterface
//    public interface CustomerFunction<S, T> extends Serializable {
//        T convert(S source);
//    }
//
//    public static void main(String[] args) throws Exception {
//        CustomerFunction<String, Long> function = Long::parseLong;
//        SerializedLambda serializedLambda = getSerializedLambda(function);
//        System.out.println(serializedLambda.getCapturingClass());
//    }
//
//    public static SerializedLambda getSerializedLambda(Serializable serializable) throws Exception {
//        Method writeReplaceMethod = serializable.getClass().getDeclaredMethod("writeReplace");
//        writeReplaceMethod.setAccessible(true);
//        return (SerializedLambda) writeReplaceMethod.invoke(serializable);
//    }
//}
}
