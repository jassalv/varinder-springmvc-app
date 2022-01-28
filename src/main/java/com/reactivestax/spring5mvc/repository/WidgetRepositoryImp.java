package com.reactivestax.spring5mvc.repository;

import com.reactivestax.spring5mvc.model.Widget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class WidgetRepositoryImp implements WidgetRepository1{

    @Autowired
    JdbcTemplate jdbcTemplate;

    private KeyHolder generatedKeyHolder = new GeneratedKeyHolder();

    @Override
    public Widget save(Widget widget) {
        String query = "insert into widget_details (Name, Description) values (?,?)";
         jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, widget.getName());
            ps.setString(2, widget.getDescription());
            return ps;
        }, generatedKeyHolder);
        Optional<Number> id =  Optional.ofNullable(generatedKeyHolder.getKey());
        if(!id.isEmpty()){
            widget.setId(Long.parseLong(String.valueOf(id.get())));
        }
        return widget;
    }

    @Override
    public Widget findById(Integer id) {
        String query = "select id, Name, Description from widget_details where id = ?";
        return jdbcTemplate.queryForObject(query, new Object[]{id}, new BeanPropertyRowMapper<>(Widget.class));
    }

    @Override
    public Integer deleteById(Integer id) {
        String query = "delete from widget_details where Id=?";
        return jdbcTemplate.update(query, id);
    }

    @Override
    public List<Widget> findAll() {
        String query = "select Id, Name, Description from widget_details";
        List<Widget> widgets = new ArrayList<>();
        List<Map<String, Object>> widgetRows = jdbcTemplate.queryForList(query);

        for (Map<String, Object> widgetRow : widgetRows) {
            Widget wid = new Widget();
            wid.setId(Long.parseLong(widgetRow.get("Id").toString()));
            wid.setName((String) widgetRow.get("Name"));
            wid.setDescription((String) widgetRow.get("Description"));
            widgets.add(wid);
        }
        return widgets;
    }

    @Override
    public Widget update(Widget widget) {
        String query = "update widget_details set Name=?, Description=? where Id=?";
        Object[] args = new Object[]{widget.getName(), widget.getDescription(), widget.getId()};
         jdbcTemplate.update(query, args);
        return widget;
    }
}
