package dev.environment.type;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Objects;

public class ParameterizedTypeImpl
        implements ParameterizedType {

    private final Type[] actualTypeArguments;
    private final Type   ownerType;
    private final Type   rawType;

    public ParameterizedTypeImpl(
            Type[] actualTypeArguments,
            Type ownerType,
            Type rawType
    ) {
        this.actualTypeArguments = actualTypeArguments;
        this.ownerType           = ownerType;
        this.rawType             = rawType;
    }

    public Type[] getActualTypeArguments() {
        return actualTypeArguments;
    }

    public Type getOwnerType() {
        return ownerType;
    }

    public Type getRawType() {
        return rawType;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        ParameterizedTypeImpl that = (ParameterizedTypeImpl) object;
        if (!Arrays.equals(actualTypeArguments, that.actualTypeArguments)) return false;
        if (!Objects.equals(ownerType, that.ownerType)) return false;
        return Objects.equals(rawType, that.rawType);
    }

    @Override
    public int hashCode() {
        int result = actualTypeArguments != null ? Arrays.hashCode(actualTypeArguments) : 0;
        result = 31 * result + (ownerType != null ? ownerType.hashCode() : 0);
        result = 31 * result + (rawType != null ? rawType.hashCode() : 0);
        return result;
    }
}