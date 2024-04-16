package hiber.dao;

import hiber.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void add(User user) {
        sessionFactory.getCurrentSession().save(user);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> listUsers() {
        TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("from User");
        return query.getResultList();
    }

//    SELECT DISTINCT u FROM User u LEFT JOIN fetch u.car

    @Override
    public List<User> getUserByCarModel(String model, int series) {
        String HQL = "SELECT user FROM User user JOIN user.car car WHERE car.model = :model AND car.series = :series";

        TypedQuery<User> userTypedQuery = sessionFactory.openSession().createQuery(HQL);

        userTypedQuery.setParameter("model", model);
        userTypedQuery.setParameter("series", series);

        return userTypedQuery.getResultList();
    }

}
