import React, {useEffect, useState} from 'react';
import styled from "styled-components";
import axios from "axios";
import {Link, useNavigate} from "react-router-dom";

function Animals() {
    const [text, setText] = useState({'name': '', 'latin_name': '', 'type': '', 'id_animal': ''});
    const [data, setData] = useState({});
    const textChange = (e) => setText(p => {
        const t = {...p};
        t[e.target.name] = e.target.value;
        return t;
    });
    const resp = async () => {
        const response = await axios.get('http://localhost:8080/api/animals/');
        for (let elem in response.data) {
            const jsonb = JSON.parse(response.data[elem]['communications']);
            for (let p in jsonb) {
                response.data[elem][p] = jsonb[p];
            }
            delete response.data[elem]['communications'];
        }
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
        for (let elem in data) if (data[elem]['type'].toLowerCase().includes(text.type.toLowerCase()) && data[elem]['name'].toLowerCase().includes(text.name.toLowerCase()) && data[elem]['latin_name'].toLowerCase().includes(text.latin_name.toLowerCase())) {
            const rows1 = [];
            for (let p in data[elem]) {
                if (p != 'id_animal') rows1.push(<RowElem key={p}>{data[elem][p]}</RowElem>);
            }
            if (rows.length == 0) {
                const rows2 = [];
                for (let p in data[elem]) {
                    if (p != 'id_animal') rows2.push(<RowElem key={p}>{p}</RowElem>);
                }
                rows.push(<RowHeader key={'header'}>{rows2}</RowHeader>);
            }
            rows.push(<Row key={data[elem]['id_animal']} id={data[elem]['id_animal']}
                           to={'/animal/' + data[elem]['id_animal'] + '/'}>{rows1}</Row>);
        }
        if (rows.length > 1) return rows; else return <div id={'no_animals'}>Таким фильтрам не удовлетворяет ни одно животное</div>;
    };
    const add = async () => {
        await axios.post('http://localhost:8080/api/animals/', {
            'id_animal': '10005',
            'name': '',
            'type': '',
            'class_field': '',
            'family': '',
            'species': '',
            'latin_name': '',
            'status': '0',
            'migrations': '0',
            'appearance': '',
            'behavior': '',
            'communications': '{}',
            'photo': '',
        });
        setTimeout(() => {
            resp();
        }, 500);
    };
    return (<div>
        <SearchWrapper>
            <div>
                <FilterWrapper>
                    <FilterPoint>{'Фильтры:'}<br/></FilterPoint>
                    <FilterPoint>
                        Latin name:
                        <InputWrapper type="text" name="latin_name" value={text['latin_name']}
                                      onChange={textChange}/><br/>
                    </FilterPoint>
                    <FilterPoint>
                        Name:
                        <InputWrapper type="text" name="name" value={text['name']} onChange={textChange}/><br/>
                    </FilterPoint>
                    <FilterPoint>
                        Type:
                        <InputWrapper type="text" name="type" value={text['type']} onChange={textChange}/><br/>
                    </FilterPoint>
                </FilterWrapper>
                <AddButton id={'add'} onClick={add}>Добавить животное</AddButton>
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

export default Animals;