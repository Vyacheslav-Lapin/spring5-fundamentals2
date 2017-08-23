package lab.aop;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.WeakHashMap;

@Aspect
@Component
public class Identification {

    private Map<Object, Long> ids = new WeakHashMap<>();

    public long getId(Object o) {
        return ids.get(o);
    }


}
