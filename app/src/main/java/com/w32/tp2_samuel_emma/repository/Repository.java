package com.w32.tp2_samuel_emma.repository;

import com.w32.tp2_samuel_emma.data.SensorDataStats;

public interface Repository<T> {

    boolean insert(T dataObject);
    T find(int id);
    T findLast();
    boolean update(T dataObject);
    boolean delete(T dataObject);
    boolean delete(int id);
}
