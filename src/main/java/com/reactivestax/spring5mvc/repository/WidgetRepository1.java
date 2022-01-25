package com.reactivestax.spring5mvc.repository;

import com.reactivestax.spring5mvc.model.Widget;

import java.util.List;

public interface WidgetRepository1{

    Widget save(Widget widget);

    Widget findById(Integer integer);

    Integer deleteById(Integer id);

    List<Widget> findAll();

    Widget update(Widget widget);
}
