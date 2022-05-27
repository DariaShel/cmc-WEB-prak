import {useParams, Link, useNavigate} from "react-router-dom";
import {useEffect, useState} from "react";
import axios from "axios";
import styled from "styled-components";


function Label() {
    const {fId} = useParams();
    const [data, setData] = useState({});
    const [showBtns, setShowBtns] = useState(false);
    const nav = useNavigate();
    useEffect(() => {
        const f = async () => {
            const response = await axios.get('http://localhost:8080/api/labels/' + fId + '/');
            setData(response.data);
        };
        f();
    }, [fId]);
    const del = async () => {
        await axios.delete('http://localhost:8080/api/labels/' + fId + '/');
        setTimeout(() => nav('/labels/', 500));
    };
    const change = async (event) => {
        const toSend = {}
        for (let elem in data) toSend[elem] = data[elem].toString();
        await axios.post('http://localhost:8080/api/labels/', toSend);
    }
    const handleChange = (event) => {
        const b = data
        b[event.target.attributes['tagname'].value] = event.target.value;
        setData({...b});

    };
    const build = () => {
        const rows1 = [];
        for (let elem in data) {
            if (elem != 'id_label') rows1.push(<FilterPoint key={elem}>{elem + ': '}<input style={{
                'marginLeft': '5px',
                'borderRadius': '10px',
                'padding': '5px',
                'border': 'none'
            }} tagname={elem} onChange={handleChange}
                                                                             type="text"
                                                                             value={data[elem]}/>
            </FilterPoint>);
        }
        if (rows1.length > 0) {
            if (showBtns == false) setShowBtns(true);
            return rows1;
        } else {
            if (showBtns == true) setShowBtns(false);
            return (<div>Метка с таким id не найдена</div>);
        }
    };
    return (
        <FilterWrapper>
            {build()}
            {showBtns ? <div><AddButton id={'del'} onClick={del} to={'/labels/'}>Удалить метку</AddButton><br/>
                <AddButton onClick={change}>Изменить</AddButton></div> : ''}
            <BackButton to={'/labels/'}>Назад</BackButton>
        </FilterWrapper>
    );
}

const FilterPoint = styled.div`
  margin-bottom: 10px;
`;
const FilterWrapper = styled.div`
  margin-right: 30px;
  border-radius: 10px;
  background-color: ghostwhite;
  padding: 10px;
  margin-bottom: 40px;
  text-align: center;
`;
const AddButton = styled.div`
  border-radius: 10px;
  padding: 10px;
  background-color: lightblue;
  cursor: pointer;
`;
const BackButton = styled(Link)`
  border-radius: 10px;
  padding: 10px;
  background-color: lightblue;
  cursor: pointer;
  text-decoration: none;
  display: block;
  margin-top: 18px;
`;

export default Label;
