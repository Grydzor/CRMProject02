package web.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import web.entity.Picture;

@Repository("pictureDAO")
public class PictureDAOImpl extends DAOImpl<Picture, Long> implements PictureDAO{
    @Autowired
    private SessionFactory factory;

    @Autowired
    protected PictureDAOImpl() {}
}
