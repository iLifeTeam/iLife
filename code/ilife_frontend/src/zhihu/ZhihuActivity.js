import React, { Component } from 'react'
import axios from 'axios';

export default class ZhihuActivity extends Component {
  constructor(props) {
    super(props);
    this.state = {
      isvalid: false,
      key: 1,
      action_text: "",
      created_time: "",
      id: 1,
      target_id: 1,
      type: "",
      excerpt: "",
      question: "",
    }
    this.getAnswer = this.getAnswer.bind(this);
  }

  componentDidMount() {
    switch (this.props.type) {
      case "VOTEUP_ANSWER":

      case "CREATE_ANSWER": {
        this.getAnswer(this.props.target_id);
        this.setState({
          key: this.props.key,
          action_text: this.props.action_text,
          created_time: this.props.created_time,
          id: this.props.id,
          target_id: this.props.target_id,
          type: this.props.type,
        })
        break;
      }
      case "FOLLOW_TOPIC":
      default:
        break;
    }
  }

  async getAnswer(id) {
    var answer;
    await axios.get("http://18.162.168.229:8090/answer?id=" + id)
      .then(function (response) {
        console.log(response);
        answer = response.data;
      })
    this.setState({
      excerpt: answer.excerpt,
      question: answer.question.excerpt,
      isvalid: true
    })
  }

  render() {
    if (this.state.isvalid)
      return (
        <tr key={this.state.key}>
          <td>{this.state.action_text}</td>
          <td>{this.state.question}</td>
          <td>{this.state.excerpt}</td>
          <td>{this.state.target_id}</td>
          <td>{this.state.created_time}</td>
        </tr>
      )
    else return null;
  }
}
