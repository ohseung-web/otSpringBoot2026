import { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import './Member.css';

export default function Procudt() {
  const [carName, setCarName] = useState('');
  const [price, setPrice] = useState('');
  const [company, setCompany] = useState('');
  const [img, setImg] = useState(null);
  const [info, setInfo] = useState('');

  const navigate = useNavigate();

  // const handleSubmit = async () => {
  //   const formData = new FormData();

  //   formData.append('carName', carName);
  //   formData.append('price', price);
  //   formData.append('company', company);
  //   formData.append('info', info);
  //   formData.append('img', img);

  //   try {
  //     const res = await axios.post('/api/cars/insert', formData);

  //     if (res.data === 1) {
  //       alert('상품등록 성공');
  //       navigate('/');
  //     }
  //   } catch (error) {
  //     console.error(error);
  //   }
  // };
  const handleSubmit = () => {
    const formData = new FormData();

    formData.append('carName', carName);
    formData.append('price', price);
    formData.append('company', company);
    formData.append('info', info);
    formData.append('img', img);

    axios
      .post('/api/cars/insert', formData)
      .then((res) => {
        if (res.data === 1) {
          alert('상품등록 성공');
          navigate('/');
        }
      })
      .catch((error) => {
        console.error(error);
        alert('등록 실패');
      });
  };

  return (
    <div id="section_wrap">
      <div className="word">상품등록</div>
      <table width="500" border="1">
        <tbody>
          <tr>
            <td>자동차이름</td>
            <td>
              <input
                type="text"
                name="carName"
                onChange={(e) => setCarName(e.target.value)}
              />
            </td>
          </tr>
          <tr>
            <td>자동차가격</td>
            <td>
              <input
                type="number"
                name="price"
                onChange={(e) => setPrice(e.target.value)}
              />
            </td>
          </tr>
          <tr>
            <td>제조사</td>
            <td>
              <input
                type="text"
                name="company"
                onChange={(e) => setCompany(e.target.value)}
              />
            </td>
          </tr>
          <tr>
            <td>자동차 이미지</td>
            <td>
              <input
                type="file"
                name="img"
                onChange={(e) => setImg(e.target.files[0])}
              />
            </td>
          </tr>
          <tr>
            <td>자동차 정보</td>
            <td>
              <input
                type="text"
                name="info"
                onChange={(e) => setInfo(e.target.value)}
              />
            </td>
          </tr>
          <tr>
            <td colSpan="2" align="center">
              <button type="button" onClick={handleSubmit}>
                상품등록
              </button>
              <button type="reset">취소</button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  );
}
