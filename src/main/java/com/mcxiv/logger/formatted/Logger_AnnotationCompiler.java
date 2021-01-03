package com.mcxiv.logger.formatted;

import com.mcxiv.logger.decorations.Decoration;
import com.mcxiv.logger.decorations.Format;
import com.mcxiv.logger.util.ByteConsumer;
import com.mcxiv.logger.util.StringConsumer;

import java.io.OutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

class Logger_AnnotationCompiler extends Logger_StreamDependencyAdder {

    public Logger_AnnotationCompiler(OutputStream stream) {
        super(stream);
    }

    public Logger_AnnotationCompiler() {
        super();
    }

    public Logger_AnnotationCompiler(ByteConsumer consumer) {
        super(consumer);
    }

    public Logger_AnnotationCompiler(StringConsumer consumer) {
        super(consumer);
    }

    private Decoration getDecoration() {
        try {
            StackTraceElement element = Thread.currentThread().getStackTrace()[3];
            Class<?> clazz = Class.forName(element.getClassName());

            Format format = null;

            for (Method m : clazz.getMethods())
                if (m.getName().equals(element.getMethodName()))
                    if (m.isAnnotationPresent(Format.class))
                        format = m.getAnnotation(Format.class);

            if (format == null && element.getMethodName().equals("<init>"))
                for (Constructor<?> c : clazz.getConstructors())
                    if (c.isAnnotationPresent(Format.class))
                        format = c.getAnnotation(Format.class);

            if (format == null)
                if (clazz.isAnnotationPresent(Format.class))
                    format = clazz.getAnnotation(Format.class);

            if (format != null) return yieldDecorator(format.value());
        } catch (Exception ignored) {
        }
        return Decoration.getRandomDecoration(yieldDecorator("").getClass());
    }

    @Override
    public void prt(String... msg) {
        Decoration decoration = getDecoration();
        writer.consume(decoration.decorate(msg));
    }

    @Override
    public void prt(Object... obj) {
        Decoration decoration = getDecoration();
        StringBuilder builder = new StringBuilder();
        for (Object o : obj) builder.append(o);
        writer.consume(decoration.decorate(builder.toString()));
    }

}
