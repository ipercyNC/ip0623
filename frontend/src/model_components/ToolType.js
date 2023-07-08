import React from 'react';
import { render } from 'react-dom';

export default class ToolType extends React.Component{
	render() {
		return (
			<tr>
				<td>{this.props.toolType.name}</td>
			</tr>
		)
	}
}