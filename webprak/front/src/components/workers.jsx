import React, {useEffect, useState} from 'react';
import styled from "styled-components";
import axios from "axios";
import {Link} from "react-router-dom";

function Workers() {
    const [text, setText] = useState({
        'surname': '',
        'name': '',
        'patronymic': '',
        'education': '',
        'work_experience': '',
        'id_employee': ''
    });
    const [data, setData] = useState({});
    const textChange = (e) => setText(p => {
        const t = {...p};
        t[e.target.name] = e.target.value;
        return t;
    });
    const resp = async () => {
        const response = await axios.get('http://localhost:8080/api/employees/');
        for (let elem in response.data) {
            for (let p in response.data[elem]) {
                if (!(p in text)) {
                    delete response.data[elem][p];
                }
            }
        }
        setData(response.data);
    };
    useEffect(() => {
        resp();
    }, []);
    const filterBuild = () => {
        const rows = [];
        for (let elem in data) if (data[elem]['surname'].toLowerCase().includes(text.surname.toLowerCase()) && data[elem]['name'].toLowerCase().includes(text.name.toLowerCase()) && data[elem]['patronymic'].toLowerCase().includes(text.patronymic.toLowerCase()) && data[elem]['education'].toLowerCase().includes(text.education.toLowerCase()) && data[elem]['work_experience'].toString().includes(text.work_experience.toLowerCase())) {
            const rows1 = [];
            for (let p in data[elem]) {
                if (p != 'id_employee') rows1.push(<RowElem key={p}>{data[elem][p]}</RowElem>);
            }
            if (rows.length == 0) {
                const rows2 = [];
                for (let p in data[elem]) {
                    if (p != 'id_employee') rows2.push(<RowElem key={p}>{p}</RowElem>);
                }
                rows.push(
                    <RowHeader key={'header'}>{rows2}</RowHeader>);
            }
            rows.push(
                <Row key={data[elem]['id_employee']} id={data[elem]['id_employee']}
                     to={'/worker/' + data[elem]['id_employee'] + '/'}>{rows1}</Row>);
        }
        if (rows.length > 1) return rows;
        else return <div id={'no_workers'}>Таким фильтрам не удовлетворяет ни один работник</div>;
    };
    const add = async () => {
        await axios.post('http://localhost:8080/api/employees/', {
            'id_employee': '10005',
            'surname': '',
            'name': '',
            'patronymic': '',
            'education': '',
            'work_experience': '0',
            'animal_species': '[]',
            'help': '[]',
            'marked': '[]',
            'photo': '',
        });
        setTimeout(() => {
            resp();
        }, 500);
    }
    return (<div>
        <SearchWrapper>
            <div>
                <FilterWrapper>
                    <FilterPoint>{'Фильтры:'}<br/></FilterPoint>
                    <FilterPoint>
                        Surname:
                        <InputWrapper type="text" name="surname" value={text['surname']}
                                      onChange={textChange}/><br/>
                    </FilterPoint>
                    <FilterPoint>
                        Name:
                        <InputWrapper type="text" name="name" value={text['name']} onChange={textChange}/><br/>
                    </FilterPoint>
                    <FilterPoint>
                        Patronymic:
                        <InputWrapper type="text" name="patronymic" value={text['type']} onChange={textChange}/><br/>
                    </FilterPoint>
                    <FilterPoint>
                        Education:
                        <InputWrapper type="text" name="education" value={text['education']} onChange={textChange}/><br/>
                    </FilterPoint>
                    <FilterPoint>
                        Work experience:
                        <InputWrapper type="text" name="work_experience" value={text['work_experience']}
                                      onChange={textChange}/><br/>
                    </FilterPoint>
                </FilterWrapper>
                <AddButton id={'add'} onClick={add}>Добавить работника</AddButton>
            </div>
            <OutputWrapper>{filterBuild()}</OutputWrapper>
        </SearchWrapper>
    </div>);
}

const InputWrapper = styled.input`
  margin-left: 5px;
  border-radius: 10px;
  padding: 5px;
  border: none;
`;
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
const OutputWrapper = styled.div`
  width: 75%;
  padding: 0 10px;
`;
const SearchWrapper = styled.div`
  display: flex;
`;
const AddButton = styled.div`
  border-radius: 10px;
  padding: 10px;
  background-color: lightblue;
  cursor: pointer;
  display: inline;
`;
const Row = styled(Link)`
  border-radius: 20px;
  background-color: lightblue;
  display: flex;
  padding: 10px;
  justify-content: space-around;
  text-decoration: none;
  margin-bottom: 5px;
`;
const RowHeader = styled.div`
  border-radius: 20px;
  background-color: pink;
  display: flex;
  padding: 10px;
  justify-content: space-around;
  text-decoration: none;
  margin-bottom: 5px;
`;
const RowElem = styled.div`
  width: 200px;
  text-align: center;
  color: black;
`;

export default Workers;