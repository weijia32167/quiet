package com.quiet.collections.queue;
import java.util.*;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Copyright tv.sohu.com
 * Author : jiawei
 * Date   : 2016/10/12
 * Desc   : 提供一种限定大小的线程安全的队列，后面加入的元素会覆盖前面的元素
 *
 */
public class LimitQueue<E> implements Deque<E> {
    //队列长度
    private final int limit;

    protected final Deque<E> queue;

    public LimitQueue(int limit){
        assert limit > 0;
        this.limit = limit;
        queue = new LinkedBlockingDeque<>(this.limit);
    }

    /********************************************入队**************************************************/
    @Override
    public boolean offer(E e){
        if(queue.size() >= limit){
            queue.poll();
        }
        return queue.offer(e);
    }

    @Override
    public boolean add(E e) {
        if(queue.size() >= limit){
            queue.poll();
        }
        return queue.add(e);
    }

    @Override
    public void addFirst(E e) {
        queue.add(e);
    }

    @Override
    public void addLast(E e) {
        queue.addLast(e);
    }

    @Override
    public boolean offerFirst(E e) {
        return queue.offerFirst(e);
    }

    @Override
    public boolean offerLast(E e) {
        return queue.offerLast(e);
    }

    @Override
    public void push(E e) {
        if(queue.size() >= limit){
            queue.pop();
        }
        queue.push(e);
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        boolean result = false;
        int size = queue.size();
        int limit = getLimit();
        int targetSize = c.size();
        if(limit - size >= targetSize){
            result = queue.addAll(c);
        }
        return result;
    }


    /*************************************************出队*************************************/
    @Override
    public E poll() {
        return queue.poll();
    }

    @Override
    public E pop() {
        return queue.pop();
    }


    @Override
    public E removeFirst() {
        return queue.removeFirst();
    }

    @Override
    public E removeLast() {
        return queue.removeLast();
    }

    @Override
    public E pollFirst() {
        return queue.pollFirst();
    }

    @Override
    public E pollLast() {
        return queue.pollLast();
    }
    @Override
    public E remove() {
        return queue.remove();
    }
    @Override
    public boolean remove(Object o) {
        return queue.remove(o);
    }
    @Override
    public boolean removeAll(Collection<?> c) {
        return queue.removeAll(c);
    }
    @Override
    public boolean removeFirstOccurrence(Object o) {
        return queue.removeFirstOccurrence(o);
    }

    @Override
    public boolean removeLastOccurrence(Object o) {
        return queue.removeFirstOccurrence(o);
    }



    /**********************************取队列元素********************************************************/
    @Override
    public E element() {
        return queue.element();
    }

    @Override
    public E peek() {
        return queue.peek();
    }

    @Override
    public E getFirst() {
        return queue.getFirst();
    }

    @Override
    public E getLast() {
        return queue.getLast();
    }

    @Override
    public E peekFirst() {
        return queue.peekFirst();
    }

    @Override
    public E peekLast() {
        return queue.peekLast();
    }
    /******************************************Units************************************************************/
    @Override
    public boolean isEmpty() {
        return queue.size() == 0 ? true : false;
    }

    @Override
    public int size() {
        return queue.size();
    }

    @Override
    public void clear() {
        queue.clear();
    }

    @Override
    public boolean contains(Object o) {
        return queue.contains(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return queue.containsAll(c);
    }

    @Override
    public Iterator<E> iterator() {
        return queue.iterator();
    }


    @Override
    public boolean retainAll(Collection<?> c) {
        return queue.retainAll(c);
    }

    @Override
    public Object[] toArray() {
        return queue.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return queue.toArray(a);
    }

    @Override
    public Iterator<E> descendingIterator() {
        return queue.descendingIterator();
    }


    /*********************************Self Function*****************************************************/
    /**
     * 获取队列
     * @return
     */
    public Queue<E> getQueue(){
        return queue;
    }

    /**
     * 获取限制大小
     * @return
     */
    public int getLimit(){
        return limit;
    }


    public boolean isFull(){
        return size() == limit;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("{");
        sb.append(queue);
        sb.append('}');
        return sb.toString();
    }
}