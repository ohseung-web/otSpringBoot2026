import { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import './Member.css'; // 작성한 CSS 연결

export default function Signup() {
  const [formData, setFormData] = useState({
    id: '',
    pw: '',
    mail: '',
    phone: '',
  });
  const navigate = useNavigate();

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSignup = () => {
    // 기존 member.js의 유효성 검사 로직
    if (!formData.id) return alert('아이디를 입력하세요');
    if (!formData.pw) return alert('비밀번호를 입력하세요');

    axios
      .post('/api/member/signup', formData)
      .then((res) => {
        // res.data가 1이면 성공, 0이면 중복, 나머지는 실패로 가정
        if (res.data === 1 || res.data === 0) {
          // 주소만 옮기는 게 아니라 state에 결과 데이터를 담아서 보냅니다.
          navigate('/member/signup_result', { state: { result: res.data } });
        } else {
          alert('회원가입 실패');
        }
      })
      .catch((err) => console.error(err));
  };

  return (
    <div id="section_wrap">
      <div className="word">회원가입</div>
      <table width="500" border="1">
        <tbody>
          <tr>
            <td>아이디</td>
            <td>
              <input type="text" name="id" onChange={handleChange} />
            </td>
          </tr>
          <tr>
            <td>비밀번호</td>
            <td>
              <input type="password" name="pw" onChange={handleChange} />
            </td>
          </tr>
          <tr>
            <td>이메일</td>
            <td>
              <input type="email" name="mail" onChange={handleChange} />
            </td>
          </tr>
          <tr>
            <td>전화번호</td>
            <td>
              <input type="tel" name="phone" onChange={handleChange} />
            </td>
          </tr>
          <tr>
            <td colSpan="2" align="center">
              <button onClick={handleSignup}>회원가입</button>
              <button type="reset">취소</button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  );
}
