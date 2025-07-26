#!/usr/bin/env python3

from flask import Flask, request
import os
import sqlite3

app = Flask(__name__)

@app.route('/')
def home():
	return "Welcome to the admin panel!"

@app.route('/exec')
def run_command():
	cmd = request.args.get('cmd')
	return os.popen(cmd).read()  # ⚠️ Command Injection

@app.route('/login')
def login():
	username = request.args.get('username')
	password = request.args.get('password')
	
	# ⚠️ SQL Injection
	conn = sqlite3.connect('users.db')
	cursor = conn.cursor()
	query = f"SELECT * FROM users WHERE username = '{username}' AND password = '{password}'"
	cursor.execute(query)
	result = cursor.fetchall()
	conn.close()
	
	if result:
		return f"Welcome, {username}!"
	else:
		return "Invalid credentials"
	
@app.route('/upload', methods=['POST'])
def upload():
	f = request.files['file']
	filepath = f"/tmp/{f.filename}"  # ⚠️ Unvalidated file path
	f.save(filepath)
	return f"File saved to {filepath}"

if __name__ == "__main__":
	app.run(debug=True)  # ⚠️ Debug mode exposes sensitive info
	