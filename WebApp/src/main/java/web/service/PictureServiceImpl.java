package web.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import web.dao.PictureDAO;
import web.entity.Picture;

@Service("pictureService")
public class PictureServiceImpl extends ServiceImpl<Picture, Long> implements PictureService{
    @Autowired
    @Qualifier("pictureDAO")
    private PictureDAO pictureDAO;

    private PictureServiceImpl() {}
}
