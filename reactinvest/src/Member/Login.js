import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './LoginForm.css';
import $ from 'jquery';

function Login() {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [loginError, setLoginError] = useState(false);
    const navigate = useNavigate();

    const handleLogin = (e) => {
        e.preventDefault();
        $.ajax({
            type: 'POST',
            url: 'http://localhost:8080/login',
            data: {username:email, password: password},
            xhrFields: {
                withCredentials: true  // 자격 증명(쿠키 등)을 포함
            },
            success: function(data) {
                console.log(data)
                if (data.token == "error") {
                    console.log("로그인 실패")
                    window.location.href = '/login?error';
                }
                else {
                    localStorage.setItem('Authorization', 'Bearer ' + data.token);
                    document.cookie = `Authorization=Bearer ${data.token}; path=/; max-age=${2 * 60 * 60}`;
                    console.log(data.name)
                    navigate(`/portfolio/${data.name}`);
                    console.log(data.name)
                }
            },
            error: function(error) {
                console.error('Error:', error);
            }
        });
    };

    const redirectToSignup = () => {
        navigate('/Member/SignUp');
    };
    return (
        <div className="main-container">
            <div className="container">
                <div className="login-container">
                    <h2>로그인</h2>
                    <div className="error-space">
                        {loginError && <p>로그인 정보가 틀렸습니다.</p>}
                    </div>
                </div>
                <form id="login-form" onSubmit={handleLogin}>
                    <label>
                        <div className="sub-check">* 이메일</div>
                        <input
                            type="email"
                            id="email"
                            name="username"
                            value={email}
                            onChange={(e) => setEmail(e.target.value)}
                            required
                        />
                    </label>
                    <br />
                    <br />
                    <label>
                        * 비밀번호
                        <br />
                        <input
                            className="pw"
                            id="password"
                            type="password"
                            name="password"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                            required
                        />
                    </label>
                    <br />
                    <br />
                    <button className="button-signup" type="submit">
                        로그인 하기
                    </button>
                </form>
                <br />
                <br />
                <h1>Social Login</h1>
                <a href="/oauth2/authorization/google">Google</a>
                <br />
                <br />
                <button className="button-signup" onClick={redirectToSignup}>
                    회원 가입
                </button>
            </div>
        </div>
    );
}


export default Login;