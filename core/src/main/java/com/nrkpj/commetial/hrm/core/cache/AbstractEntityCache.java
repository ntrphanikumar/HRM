package com.nrkpj.commetial.hrm.core.cache;

import static au.com.bytecode.opencsv.CSVParser.DEFAULT_QUOTE_CHARACTER;
import static au.com.bytecode.opencsv.CSVParser.DEFAULT_SEPARATOR;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.core.io.Resource;

import au.com.bytecode.opencsv.CSVReader;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

public abstract class AbstractEntityCache<E> {
    private Class<?> clazz;

    private final File seedFile;

    private final Set<E> entities = Sets.newLinkedHashSet();

    private static final Set<Class<?>> NUMERIC_PRIMITIVE_CLASSES = Sets.<Class<?>> newHashSet(int.class, short.class, long.class, float.class,
            double.class);

    AbstractEntityCache(Resource seedFile) throws IOException {
        this.seedFile = seedFile.getFile();
    }

    protected void init() throws IOException, IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException {
        this.clazz = getEntityClass();
        CSVReader reader = new CSVReader(new FileReader(seedFile), DEFAULT_SEPARATOR, DEFAULT_QUOTE_CHARACTER);
        List<PropertyDescriptor> fields = getPropertyDescriptorsForFieldNames(reader.readNext());
        for (String[] colValues = reader.readNext(); colValues != null; colValues = reader.readNext()) {
            if (isValidEntry(fields, colValues)) {
                E instance = buildInstanceFromRow(fields, colValues);
                entities.add(instance);
                onEntityAdd(instance);
            }
        }
        reader.close();
    }

    protected void onEntityAdd(E entity) {

    }

    protected Object getTransformedValue(String value, Class<?> toClass) {
        if (isNumericClass(toClass)) {
            return StringUtils.isNotBlank(value) ? NumberUtils.createNumber(value) : 0;
        }
        if (isBooleanClass(toClass)) {
            return Boolean.parseBoolean(value);
        }
        return value;
    }

    public Set<E> getEntities() {
        return entities;
    }

    private boolean isNumericClass(Class<?> clazz) {
        return Number.class.isAssignableFrom(clazz) || NUMERIC_PRIMITIVE_CLASSES.contains(clazz);
    }

    private boolean isBooleanClass(Class<?> clazz) {
        return Boolean.class.equals(clazz) || boolean.class.equals(clazz);
    }

    @SuppressWarnings("unchecked")
    private E buildInstanceFromRow(List<PropertyDescriptor> fields, String[] colValues) throws InstantiationException, IllegalAccessException,
            InvocationTargetException {
        E instance = (E) clazz.newInstance();
        for (int i = 0; i < fields.size(); i++) {
            PropertyDescriptor pd = fields.get(i);
            pd.getWriteMethod().invoke(instance, getTransformedValue(colValues[i], pd.getPropertyType()));
        }
        return instance;
    }

    private List<PropertyDescriptor> getPropertyDescriptorsForFieldNames(String[] fieldNames) throws IOException, IntrospectionException {
        List<PropertyDescriptor> propertyDescriptors = Lists.newArrayList();
        for (String fieldName : fieldNames) {
            propertyDescriptors.add(BeanUtils.getPropertyDescriptor(clazz, fieldName));
        }
        return propertyDescriptors;
    }

    @SuppressWarnings("unchecked")
    protected Class<E> getEntityClass() {
        ParameterizedType parameterizedType = (ParameterizedType) this.getClass().getGenericSuperclass();
        return (Class<E>) parameterizedType.getActualTypeArguments()[0];
    }

    private boolean isValidEntry(List<PropertyDescriptor> fields, String[] colValues) {
        if (colValues.length > 0 && colValues.length < fields.size()) {
            return false;
        }
        return true;
    }
}
