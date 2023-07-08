
import React from 'react';
import { render } from 'react-dom';
import ToolType from './ToolType';

export default class ToolTypeList extends React.Component{
	render() {
		const toolTypes = this.props.toolTypes.map(toolType =>
			<ToolType key={toolType.id} toolType={toolType}/>
		);
		return (
			<table>
				<tbody>
					<tr>
						<th>Name</th>
					</tr>
					{toolTypes}
				</tbody>
			</table>
		)
	}
}
