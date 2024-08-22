import React, { useState } from 'react';

function Signup() {
    const [email, setEmail] = useState('');
    const [name, setName] = useState('');
    const [password, setPassword] = useState('');
    const [passwordCheck, setPasswordCheck] = useState('');
    const [emailFlag, setEmailFlag] = useState(false);
    const [nameFlag, setNameFlag] = useState(false);
    const [passwordFlag, setPasswordFlag] = useState(false);

    const handleEmailCheck = () => {
        if (email.indexOf('@') === -1) {
            setEmailFlag(false);
            alert('이메일 양식을 지켜주세요');
        } else {
            fetch('http://localhost:8080/login/checkEmail', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ email }),
            })
                .then(response => response.json())
                .then(result => {
                    if (!result) {
                        setEmailFlag(true);
                    } else {
                        setEmailFlag(false);
                        alert('이메일 중복 체크 실패');
                    }
                });
        }
    };

    const handleNameCheck = () => {
        fetch('http://localhost:8080/login/checkName', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ name }),
        })
            .then(response => response.json())
            .then(result => {
                if (!result) {
                    setNameFlag(true);
                } else {
                    setNameFlag(false);
                    alert('이름 중복 체크 실패');
                }
            });
    };

    const handleSubmit = (e) => {
        e.preventDefault();

        if (password === passwordCheck) {
            setPasswordFlag(true);
        } else {
            setPasswordFlag(false);
            alert('비밀번호를 다시 입력해주세요');
            return;
        }

        if (!emailFlag || !nameFlag) {
            alert('중복 체크를 완료해주세요.');
            return;
        }

        if (password === '' || passwordCheck === '') {
            alert('비밀번호를 입력해주세요.');
            return;
        }

        if (emailFlag && nameFlag && passwordFlag) {
            // Form submission logic (fetch or axios)
            fetch('http://localhost:8080/login/signup', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ email, name, password }),
            })
                .then(response => response.json())
                .then(data => {
                    console.log('가입 성공:', data);
                })
                .catch(error => console.error('Error:', error));
        }
    };

    return (
        <div className="main-container">
            <div className="container">
                <h2>회원가입을 위해<br />정보를 입력해주세요.</h2>
                {/* Error message */}
                <form id="signup-form" onSubmit={handleSubmit}>
                    <label>
                        <div className="sub-check">* 이메일
                            <button type="button" className="button-check" onClick={handleEmailCheck}>중복 체크</button>
                        </div>
                        <input
                            type="email"
                            id="email"
                            value={email}
                            onChange={(e) => setEmail(e.target.value)}
                            required
                        />
                    </label>
                    <br /><br />
                    <label>
                        <div className="sub-check">* 이름
                            <button type="button" className="button-check" onClick={handleNameCheck}>중복 체크</button>
                        </div>
                        <input
                            type="text"
                            id="name"
                            value={name}
                            onChange={(e) => setName(e.target.value)}
                            required
                        />
                    </label>
                    <br /><br />
                    <label>
                        * 비밀번호<br />
                        <input
                            className="pw"
                            id="password"
                            type="password"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                            required
                        />
                    </label>
                    <br /><br />
                    <label>
                        * 비밀번호 확인<br />
                        <input
                            className="pw"
                            id="password-check"
                            type="password"
                            value={passwordCheck}
                            onChange={(e) => setPasswordCheck(e.target.value)}
                            required
                        />
                    </label>
                    <br /><br />
                    <button type="submit" className="button-signup">가입하기</button>
                </form>
            </div>
        </div>
    );
}

export default Signup;
