package com.quiet.util;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.security.AccessController;
import java.security.PrivilegedExceptionAction;
import java.util.Comparator;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.Unsafe;


public final class UnsafeUtil {

    public static final Logger LOGGER = LoggerFactory.getLogger(UnsafeUtil.class);

    private static final Unsafe THE_UNSAFE;
    private static final int OBJECT_REF_SIZE;
    /**
     * JVM指针长度: 32bit 64bit
     **/
    private static final int ADDRESS_SIZE;

    private static final long OBJECT_ARRAY_BASE_ADDRESS;
    private static final int OBJECT_HEADER_SIZE;

    private static final ThreadLocal<Object[]> OBJECT_START_ADDRESS_HELPER = new ThreadLocal<>();

    static {
        try {
            final PrivilegedExceptionAction<Unsafe> action = new PrivilegedExceptionAction<Unsafe>() {
                public Unsafe run() throws Exception {
                    Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
                    theUnsafe.setAccessible(true);
                    return (Unsafe) theUnsafe.get(null);
                }
            };
            THE_UNSAFE = AccessController.doPrivileged(action);
            OBJECT_REF_SIZE = THE_UNSAFE.arrayIndexScale(Object[].class);
            ADDRESS_SIZE = THE_UNSAFE.addressSize();
            OBJECT_ARRAY_BASE_ADDRESS = THE_UNSAFE.arrayBaseOffset(Object[].class);
            Object[] objects1 = new Object[1];
            Object[] objects2 = new Object[1];
            objects1[0] = new Object();
            objects2[0] = new Object();
            long object1Address, object2Address;
            switch (ADDRESS_SIZE) {
                case 4:
                    object1Address = THE_UNSAFE.getInt(objects1, OBJECT_ARRAY_BASE_ADDRESS);
                    object2Address = THE_UNSAFE.getInt(objects2, OBJECT_ARRAY_BASE_ADDRESS);
                    OBJECT_HEADER_SIZE = (int) (object2Address - object1Address);
                    break;
                case 8:
                    object1Address = THE_UNSAFE.getLong(objects1, OBJECT_ARRAY_BASE_ADDRESS);
                    object2Address = THE_UNSAFE.getLong(objects2, OBJECT_ARRAY_BASE_ADDRESS);
                    OBJECT_HEADER_SIZE = (int) (object2Address - object1Address) * 8;
                    break;
                default:
                    throw new Error("unsupported address size: " + ADDRESS_SIZE);
            }
        } catch (Exception e) {
            throw new RuntimeException("Unable to load unsafe", e);
        }
    }

    /**
     * Get a handle on the Unsafe instance, used for accessing low-level concurrency
     * and memory constructs.
     *
     * @return The Unsafe
     */
    public static Unsafe getUnsafe() {
        return THE_UNSAFE;
    }

    /**
     * @return 返回当前JVM环境下一个引用占用byte大小【可能是4或者8】
     */
    public static final int getObjectRefSize() {
        return OBJECT_REF_SIZE;
    }

    /**
     * @return 返回当前JVM环境下地址总线长度【如果是32bit的返回4byte，64bit的返回8byte】
     */
    public static final int getAddressSize() {
        return ADDRESS_SIZE;
    }

    /**
     * @return 返回当前JVM环境下对象头大小
     */
    public static final int getObjectHeaderSize() {
        return OBJECT_HEADER_SIZE;
    }

    /**
     * mark word oop的size依赖JVM是32bit还是64bit
     * PS: 不使用UnSafe.getAddress()的原因是这东西总是返回long(8byte)，32bit的JVM的mark word oop只有4byte，这个方法会自动帮忙padding成long。
     * 这个方法实现严格的mark word oop大小。
     */
    public static final byte[] markWordOop(Object object) {
        long address = address(object);
        return ADDRESS_SIZE == 4 ? NumberUtil.int2Bytes(THE_UNSAFE.getInt(address)) : NumberUtil.long2Bytes(THE_UNSAFE.getLong(address));
    }

    /**
     * klass的起始偏移依赖JVM是32bit还是64bit；32bit的JVM的偏移为4,64bit的偏移为8。【偏移以byte为单位】
     * klass的长度依赖是否JVM heap大小，超过32G会用8byte，绝大部分情况采用4byte。
     */
    public static final byte[] klassOop(Object object) {
        long address = address(object);
        long memory = Runtime.getRuntime().totalMemory() / 1024 / 1024 / 1024;
        if (memory > 32) {
            return ADDRESS_SIZE == 4 ? NumberUtil.long2Bytes(THE_UNSAFE.getLong(address + 4)) : NumberUtil.long2Bytes(THE_UNSAFE.getLong(address + 8));
        } else {
            return ADDRESS_SIZE == 4 ? NumberUtil.int2Bytes(THE_UNSAFE.getInt(address + 4)) : NumberUtil.int2Bytes(THE_UNSAFE.getInt(address + 8));
        }
    }


    public static final int getInt(Object object, String fieldName) throws NoSuchFieldException {
        Field field = object.getClass().getDeclaredField(fieldName);
        long offset = THE_UNSAFE.objectFieldOffset(field);
        long address = address(object);
        return THE_UNSAFE.getInt(address + offset);
    }

    public static final long getLong(Object object, String fieldName) throws NoSuchFieldException {
        Field field = object.getClass().getDeclaredField(fieldName);
        long offset = THE_UNSAFE.objectFieldOffset(field);
        long address = address(object);
        return THE_UNSAFE.getLong(address + offset);
    }

    public static final byte getByte(Object object, String fieldName) throws NoSuchFieldException {
        Field field = object.getClass().getDeclaredField(fieldName);
        long offset = THE_UNSAFE.objectFieldOffset(field);
        long address = address(object);
        return THE_UNSAFE.getByte(address + offset);
    }

    public static final char getChar(Object object, String fieldName) throws NoSuchFieldException {
        Field field = object.getClass().getDeclaredField(fieldName);
        long offset = THE_UNSAFE.objectFieldOffset(field);
        long address = address(object);
        return THE_UNSAFE.getChar(address + offset);
    }

    public static final boolean getBoolean(Object object, String fieldName) throws NoSuchFieldException {
        Field field = object.getClass().getDeclaredField(fieldName);
        long offset = THE_UNSAFE.objectFieldOffset(field);
        return THE_UNSAFE.getBoolean(object, offset);
    }


    public static final short getShort(Object object, String fieldName) throws NoSuchFieldException {
        Field field = object.getClass().getDeclaredField(fieldName);
        long offset = THE_UNSAFE.objectFieldOffset(field);
        return THE_UNSAFE.getShort(object, offset);
    }


    public static final float getFloat(Object object, String fieldName) throws NoSuchFieldException {
        Field field = object.getClass().getDeclaredField(fieldName);
        long offset = THE_UNSAFE.objectFieldOffset(field);
        long address = address(object);
        return THE_UNSAFE.getFloat(address + offset);
    }


    public static final double getDouble(Object object, String fieldName) throws NoSuchFieldException {
        Field field = object.getClass().getDeclaredField(fieldName);
        long offset = THE_UNSAFE.objectFieldOffset(field);
        long address = address(object);
        return THE_UNSAFE.getDouble(address + offset);
    }

    public static final byte[][] getBytes(Object object) {
        long address = UnsafeUtil.address(object);
        long size = UnsafeUtil.shallowSizeof(object);
        long offset = size % 8 == 0 ? size / 8 : (size / 8) + 1;
        if (offset <= 0) {
            throw new IllegalArgumentException("offset must > 0!");
        } else {
            byte[][] result = new byte[(int) offset][8];
            long temp;
            for (int i = 0; i < offset; i++) {
                temp = THE_UNSAFE.getLong(address + i * 8);
                result[i] = NumberUtil.long2Bytes(temp);
            }
            return result;
        }
    }


    /**
     * @param object          目标对象
     * @param referenceObject true : 目标对象的引用对象计算真实大小
     *                        false: 目标对象内部的引用对象只计算这个引用的大小
     * @return 内存大小
     */
    private static final long sizeOf(Object object, boolean referenceObject) {
        long result = 0;
        Class<?> clazz = object.getClass();
        if (clazz.isArray()) {
            long baseOffset = THE_UNSAFE.arrayBaseOffset(clazz);
            long indexScale = THE_UNSAFE.arrayIndexScale(clazz);
            if (!referenceObject) {
                result = baseOffset + indexScale * Array.getLength(object);
            } else {
                result = baseOffset;
                for (int i = 0; i < Array.getLength(object); i++) {
                    Object arrayElement = Array.get(object, i);
                    if (arrayElement == null) {
                        continue;
                    } else {
                        result += sizeOf(arrayElement, referenceObject);
                    }
                }
            }
        } else {

            TreeSet<Field> fields = new TreeSet<>(new Comparator<Field>() {
                @Override
                public int compare(Field o1, Field o2) {
                    return (int) (THE_UNSAFE.objectFieldOffset(o1) - THE_UNSAFE.objectFieldOffset(o2));
                }
            });

            TreeSet<Field> referenceFields = new TreeSet<>(new Comparator<Field>() {
                @Override
                public int compare(Field o1, Field o2) {
                    return (int) (THE_UNSAFE.objectFieldOffset(o1) - THE_UNSAFE.objectFieldOffset(o2));
                }
            });

            while (clazz != Object.class) {
                for (Field f : clazz.getDeclaredFields()) {
                    if ((f.getModifiers() & Modifier.STATIC) == 0) {
                        fields.add(f);
                        if (!f.getType().isPrimitive()) {
                            referenceFields.add(f);
                        }
                    }
                }
                clazz = clazz.getSuperclass();
            }
            long maxOffset = Long.MIN_VALUE, minOffset = Long.MAX_VALUE;

            for (Field f : fields) {
                long offset = THE_UNSAFE.objectFieldOffset(f);
                if (offset > maxOffset) {
                    maxOffset = offset;
                }
                if (offset < minOffset) {
                    minOffset = offset;
                }
            }

            if (fields.size() != 0) {
                result = ((maxOffset / 8) + 1) * 8;   // padding*//*
                if (referenceObject) {
                    for (Field referenceField : referenceFields) {
                        referenceField.setAccessible(true); //设置些属性是可以访问的
                        try {
                            Object val = referenceField.get(object);//得到此属性的值
                            if (val == null) {
                                continue;
                            } else {
                                result += sizeOf(val, referenceObject);
                            }
                        } catch (Throwable throwable) {
                            throwable.printStackTrace();
                        }
                    }
                }
            } else {
                result = OBJECT_HEADER_SIZE;
            }
        }
        return result;
    }

    /**
     * @param object
     * @return 当前对象在内存占用的内存大小(byte)
     */
    public static final long shallowSizeof(Object object) {
        return sizeOf(object, false);
    }


    /**
     * @return 返回对象的起始地址，单位是byte。
     * JVM所有对象大小都是8byte的整数倍(8byte对齐),sun.misc.Unsafe#getInt() or getLong()获取的都是实际物理地址/8后的结果返回，
     * 这个方法根据利用>>3(*8)来获取实际的物理内存地址。
     */
    public static long address(Object o) {
        if (o == null) {
            throw new NullPointerException();
        }
        Object[] array = OBJECT_START_ADDRESS_HELPER.get();
        if (array == null) {
            array = new Object[1];
            OBJECT_START_ADDRESS_HELPER.set(array);
        }
        array[0] = o;
        long objectAddress;
        switch (ADDRESS_SIZE) {
            case 4:
                objectAddress = THE_UNSAFE.getInt(array, OBJECT_ARRAY_BASE_ADDRESS);
                break;
            case 8:
                objectAddress = THE_UNSAFE.getLong(array, OBJECT_ARRAY_BASE_ADDRESS) << 3;
                break;
            default:
                throw new RuntimeException("unsupported address size: " + ADDRESS_SIZE);
        }
        return objectAddress;
    }


}