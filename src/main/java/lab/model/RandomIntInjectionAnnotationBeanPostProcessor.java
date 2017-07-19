package lab.model;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.lang.Nullable;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Random;

public class RandomIntInjectionAnnotationBeanPostProcessor implements BeanPostProcessor {

    private static final Random rand = new Random();

    @Nullable
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Field[] fields = bean.getClass().getDeclaredFields();
        for (Field field : fields) {
            RandomIntInjection annotation = field.getAnnotation(RandomIntInjection.class);
            if (annotation != null) {
                int min = annotation.min();
                int max = annotation.max();

                int randomizedAge = rand.nextInt(max - min) + min;

                field.setAccessible(true);
                ReflectionUtils.setField(field, bean, randomizedAge);
            }
        }

        return bean;
    }

    @Nullable
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
