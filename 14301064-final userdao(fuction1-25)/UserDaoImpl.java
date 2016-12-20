package cn.edu.bjtu.weibo.daoimpl;

import java.util.List;

import cn.edu.bjtu.weibo.dao.UserDAO;
import cn.edu.bjtu.weibo.model.User;
import redis.clients.jedis.Jedis;

public class UserDaoImpl implements UserDAO {

	static String host = "127.0.0.1";
	static int port = 6379;
	Jedis client = new Jedis(host, port);

	@Override
	public String insertNewUser(User user) {
		String result;
		String key_name = "User:" + String.valueOf(user.getId());
		result = client.set(key_name + ":id", String.valueOf(user.getId()));
		result = client.set(key_name + ":Name", user.getName());
		result = client.set(key_name + ":Birthday", user.getBirthday());
		result = client.set(key_name + ":Sex", user.getSex());
		result = client.set(key_name + ":Location", user.getLocation());
		result = client.set(key_name + ":Introduction", user.getIntroduction());
		result = client.set(key_name + ":LastWeiboId", String.valueOf(user.getLastWeiboId()));
		result = client.set(key_name + ":Qq", user.getQq());
		result = client.set(key_name + ":Email", user.getEmail());
		result = client.set(key_name + ":Age", String.valueOf(user.getAge()));
		result = client.set(key_name + ":PhoneNumber", user.getPhone());
		result = client.set(key_name + ":Education", user.getEducation());
		return result;
	}

	@Override
	public boolean setState(String id, boolean s) {
		String state;
		if (s) {
			state = "true";
		} else {
			state = "false";
		}
		if (client.exists("User:" + id + ":State")) {
			client.set("User:" + id + ":State", state);
			return true;
		}
		return false;
	}

	@Override
	public String getUserName(String userId) {
		return client.get("User:" + userId + ":Name");
	}

	@Override
	public boolean updateUserName(String userId, String userName) {
		String result = client.set("User:" + userId + ":Name", userName);
		if (result.equals("OK")) {
			return true;
		}
		return false;
	}

	@Override
	public String getUserIntroduction(String userId) {

		return client.get("User:" + userId + ":Introduction");
	}

	@Override
	public boolean updateUserIntroduction(String userId, String introduction) {
		String result = client.set("User:" + userId + ":Introduction", introduction);
		if (result.equals("OK")) {
			return true;
		}
		return false;
	}

	@Override
	public String getUserPhoneNumber(String userId) {
		return client.get("User:" + userId + ":PhoneNumber");
	}

	@Override
	public boolean updateUserPhoneNumber(String userId, String phoneNumber) {
		String result = client.set("User:" + userId + ":PhoneNumber", phoneNumber);
		if (result.equals("OK")) {
			return true;
		}
		return false;
	}

	@Override
	public String getUserAge(String userId) {
		return client.get("User:" + userId + ":Age");
	}

	@Override
	public boolean updateUserAge(String userId, String age) {
		String result = client.set("User:" + userId + ":Age", age);
		if (result.equals("OK")) {
			return true;
		}
		return false;
	}

	@Override
	public String getUserEmail(String userId) {
		return client.get("User:" + userId + ":Email");
	}

	@Override
	public boolean updateUserEmail(String userId, String email) {
		String result = client.set("User:" + userId + ":Email", email);
		if (result.equals("OK")) {
			return true;
		}
		return false;
	}

	@Override
	public String getUserQQ(String userId) {
		return client.get("User:" + userId + ":Qq");
	}

	@Override
	public boolean updateUserQQ(String userId, String qq) {
		String result = client.set("User:" + userId + ":Qq", qq);
		if (result.equals("OK")) {
			return true;
		}
		return false;
	}

	@Override
	public String getUserEducation(String userId) {
		return client.get("User:" + userId + ":Education");
	}

	@Override
	public boolean updateUserEducation(String userId, String education) {
		String result = client.set("User:" + userId + ":Education", education);
		if (result.equals("OK")) {
			return true;
		}
		return false;
	}

	@Override
	public String getLocation(String userId) {
		return client.get("User:" + userId + ":Location");
	}

	@Override
	public String updateLocation(String userId, String location) {
		String result = client.set("User:" + userId + ":Location", location);

		return result;
	}

	@Override
	public String getBirthday(String userId) {
		return client.get("User:" + userId + ":Birthday");
	}

	@Override
	public String updateBirthday(String userId, String birthday) {
		String result = client.set("User:" + userId + ":Birthday", birthday);

		return result;
	}

	@Override
	public String getSex(String userId) {
		return client.get("User:" + userId + ":Sex");
	}

	@Override
	public String updateSex(String userId, String sex) {
		String result = client.set("User:" + userId + ":Sex", sex);

		return result;
	}

	@Override
	public String getLastWeiboId(String userId) {
		return client.get("User:" + userId + ":LastWeiboId");
	}

	@Override
	public String updateLastWeiboId(String userId, String weiboId) {
		String result = client.set("User:" + userId + ":LastWeiboId", weiboId);

		return result;
	}

	@Override
	public String getWeiboNumber(String userId) {
		return client.get("User:" + userId + ":WeiboNumber");
	}

	@Override
	public String updateWeiboNumber(String userId, String weiboNumber) {
		String key = "User:" + userId + ":CommentWeibo";
		Jedis jedis = new Jedis("localhost");
		long length = jedis.llen(key);
		return String.valueOf(length);
	}

	@Override
	public List<String> getWeibo(String userId, int pageIndex, int pagePerNumber) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean insertWeibo(String userId, String weiboId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteWeibo(String userId, String weiboId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getFollowerNumber(String userId) {
		// TODO Auto-generated method stub
		String key = "User:" + userId + ":FansNumber";
		Jedis jedis = new Jedis("localhost");
		return jedis.get(key);
	}

	@Override
	public String updateFollowerNumber(String userId, String followerNumber) {
		// TODO Auto-generated method stub
		String key = "User:" + userId + ":Fans";
		Jedis jedis = new Jedis("localhost");
		long length = jedis.llen(key);
		return String.valueOf(length);
	}

	@Override
	public String getFollowingNumber(String userId) {

		String key = "User:" + userId + ":AttentionNumber";
		Jedis jedis = new Jedis("localhost");
		return jedis.get(key);
	}

	@Override
	public String updateFollowingNumber(String userId, String followeingNumber) {
		String key = "User:" + userId + ":Attention";
		Jedis jedis = new Jedis("localhost");
		long length = jedis.llen(key);
		return String.valueOf(length);
	}

	@Override
	public List<String> getFollowers(String userId, int pageIndex, int pagePerNumber) {
		Jedis jedis = new Jedis("localhost");
		String key = "User:"+userId+":Fans";
		List<String> list = jedis.lrange(key, 0, -1);  
		return list;
	}

	@Override
	public boolean insertFollower(String userId, String followerId) {
		// TODO Auto-generated method stub
		Jedis jedis = new Jedis("localhost");
		String key = "User:"+userId+":Fans";
		if(jedis.lpush(key, followerId)>0){
			return true;
		};
		return false;
	}

	@Override
	public boolean deleteFollower(String userId, String followerId) {
		// TODO Auto-generated method stub
		Jedis jedis = new Jedis("localhost");
		String key = "User:"+userId+":Fans";
		if(jedis.lrem(key, 0, followerId)==1){
			return true;
		}
		return false;
	}

	@Override
	public List<String> getFollowing(String userId, int pageIndex, int pagePerNumber) {
		// TODO Auto-generated method stub
		Jedis jedis = new Jedis("localhost");
		String key = "User:"+userId+":Attention";
		List<String> list = jedis.lrange(key, 0, -1);  
		return list;
	}

	@Override
	public boolean insertFollowing(String userId, String followingId) {
		// TODO Auto-generated method stub
		Jedis jedis = new Jedis("localhost");
		String key = "User:"+userId+":Attention";
		if(jedis.lpush(key, followingId)>0){
			return true;
		}
		return false;
	}

	@Override
	public boolean deleteFollowing(String userId, String followingId) {
		// TODO Auto-generated method stub
		Jedis jedis = new Jedis("localhost");
		String key = "User:"+userId+":Attention";
		if(jedis.lrem(key, 0, followingId)==1){
			return true;
		}
		return false;
	}

	@Override
	public List<String> getForwordWeibo(String userId, int pageIndex, int pagePerNumber) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean insertForwordWeibo(String userId, String weiboId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteForwordWeibo(String userId, String weiboId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<String> getLikeWeibo(String userId, int pageIndex, int pagePerNumber) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean insertLikeWeibo(String userId, String weiboId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteLikeWeibo(String userId, String weiboId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<String> getCommentWeibo(String userId, int pageIndex, int pagePerNumber) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean insertCommentWeibo(String userId, String weiboId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteCommentWeibo(String userId, String weiboId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<String> getFavoriteWeibo(String userId, int pageIndex, int pagePerNumber) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean insertFavoriteWeibo(String userId, String weiboId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteFavoriteWeibo(String userId, String weiboId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<String> getGroups(String userId, int pageIndex, int pagePerNumber) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean insertNewGroup(String userId, String group) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteGroup(String userId, String group) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<String> getUsersByGroup(String userId, String group, int pageIndex, int pagePerNumber) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean insertUserToGroup(String userId, String group, String followingUserId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteUserFromGroup(String userId, String group, String followingUserId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<String> getWeiboAtMeUnread(String userId, int pageIndex, int pagePerNumber) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getWeiboAtMeRead(String userId, int pageIndex, int pagePerNumber) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean insertWeiboAtMe(String userId, String weiboId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteWeiboAtMe(String userId, String weiboId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<String> getWeiboCommentMeUnread(String userId, int pageIndex, int pagePerNumber) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getWeiboCommentMeRead(String userId, int pageIndex, int pagePerNumber) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean insertWeiboCommentMe(String userId, String weiboId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteWeiboCommentMe(String userId, String weiboId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<String> getWeiboLikeMeUnread(String userId, int pageIndex, int pagePerNumber) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getWeiboLikeMeRead(String userId, int pageIndex, int pagePerNumber) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean insertWeiboLikeMe(String userId, String weiboId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteWeiboLikeMe(String userId, String weiboId) {
		// TODO Auto-generated method stub
		return false;
	}

}
