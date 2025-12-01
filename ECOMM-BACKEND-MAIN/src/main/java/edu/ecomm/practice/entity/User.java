package edu.ecomm.practice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name = "users")
public class User {
		
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private int userId;
		@Column(unique=true)
		private String username;
		private String password;
		private String role;
		
		
		public User() {
			super();
			// TODO Auto-generated constructor stub
		}


		public User(int userId, String username, String password, String role) {
			super();
			this.userId = userId;
			this.username = username;
			this.password = password;
			this.role = role;
		}


		public int getUserId() {
			return userId;
		}


		public void setUserId(int userId) {
			this.userId = userId;
		}


		public String getUserName() {
			return username;
		}


		public void setUserName(String username) {
			this.username = username;
		}


		public String getPassword() {
			return password;
		}


		public void setPassword(String password) {
			this.password = password;
		}


		public String getRole() {
			return role;
		}


		public void setRole(String role) {
			this.role = role;
		}


		@Override
		public String toString() {
			return "User [userId=" + userId + ", userName=" + username + ", password=" + password + ", role=" + role
					+ "]";
		}
		
		
		
		
}
