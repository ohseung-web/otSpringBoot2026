import { useState, useContext } from 'react'; // 1. useContext 임포트
import { AuthContext } from '../contexts/AuthContext'; // 2. Context 임포트
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import './Member.css';

export default function Login() {
  const [formData, setFormData] = useState({ id: '', pw: '' });
  const navigate = useNavigate();

  // 3. 전역 저장소의 login 함수 가져오기
  const { login } = useContext(AuthContext);

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleLogin = () => {
    axios
      .post('/api/member/login', formData)
      .then((res) => {
        if (res.data) {
          alert(`${res.data.id}님 환영합니다!`);

          // 4. Context의 login 함수 호출 (세션 저장 + 상태 업데이트가 한 번에 됨)
          login(res.data);

          navigate('/');
        } else {
          alert('아이디 또는 비밀번호를 확인하세요.');
        }
      })
      .catch((err) => console.error(err));
  };

  return (
    <div id="section_wrap">
      <div className="word">로그인</div>
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
            <td colSpan="2" align="center">
              <button onClick={handleLogin}>로그인</button>
              <button type="reset">취소</button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  );
}
