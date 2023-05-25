import { ProColumns, ProFormInstance, ProTable } from '@ant-design/pro-components';
import '@umijs/max';
import { Modal } from 'antd';
import React, { useEffect, useRef } from 'react';

export type CreateProps = {
  columns: ProColumns<API.InterfaceUpdateRequest>[];
  // @ts-ignore
  onCancel: () => void;
  onSubmit: (values: API.InterfaceUpdateRequest) => Promise<void>;
  visible: boolean;
  values: API.InterfaceInfo;
};
const CreateModel: React.FC<CreateProps> = (props) => {
  const { visible, columns, onCancel, onSubmit, values } = props;
  const formRef = useRef<ProFormInstance>();
  useEffect(() => {
    if (formRef) {
      formRef.current?.setFieldsValue(values);
    }
  }, [values]);
  return (
    <Modal visible={visible} onCancel={() => onCancel?.()} footer={null}>
      <ProTable
        type={'form'}
        columns={columns}
        // todo 设置values动态
        formRef={formRef}
        form={{
          initialValues: values,
        }}
        onSubmit={async (value) => {
          onSubmit?.(value);
        }}
      />
    </Modal>
  );
};
export default CreateModel;
